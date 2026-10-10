package com.shilapi.xcertplay.bluetooth

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.anwsdk.service.IAnwPhoneLink
import com.anwsdk.service.IAnwSPPDataCallBack
import com.anwsdk.service.IAnwSocketDataCallBack
import com.shilapi.xcertplay.transport.BlockingDuplexByteStream
import java.io.IOException
import java.util.ArrayDeque
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * [BlockingDuplexByteStream] implemented over Nissan HSAE / AnwSdk Bluetooth service ([IAnwPhoneLink]).
 * Bypasses AOSP BluetoothAdapter restrictions by communicating directly with the car's native Bluetooth daemon.
 */
class HsaeRfcommStream private constructor(
    private val context: Context,
    private val service: IAnwPhoneLink,
    private val connectionIndex: Int,
    private val isSocketMode: Boolean,
    private val connection: ServiceConnection,
    private val socketCallback: IAnwSocketDataCallBack.Stub?,
    private val sppCallback: IAnwSPPDataCallBack.Stub?,
) : BlockingDuplexByteStream {

    private val lock = Object()
    private val sendLock = Object()
    private val pending = ArrayDeque<ByteArray>()
    private var pendingBytes = 0
    private var closed = false
    private var peerEnded = false
    private var failure: IOException? = null

    internal fun onDataReceived(data: ByteArray, length: Int) {
        val payload = if (data.size == length) data else data.copyOf(length)
        synchronized(lock) {
            if (closed || peerEnded) return
            pending.addLast(payload)
            pendingBytes += payload.size
            lock.notifyAll()
        }
    }

    internal fun onDisconnected() {
        synchronized(lock) {
            peerEnded = true
            lock.notifyAll()
        }
    }

    override fun send(data: ByteArray) {
        synchronized(sendLock) {
            synchronized(lock) {
                failure?.let { throw it }
                if (closed) throw IOException("HSAE RFCOMM stream is closed")
            }
            val written = IntArray(1)
            var offset = 0
            while (offset < data.size) {
                val chunkSize = minOf(data.size - offset, 4096)
                val chunk = if (offset == 0 && chunkSize == data.size) data else data.copyOfRange(offset, offset + chunkSize)
                val ret = try {
                    if (isSocketMode) {
                        service.ANWBT_SocketWrite(connectionIndex, chunk, chunk.size, written)
                    } else {
                        service.ANWBT_SPPWrite(connectionIndex, chunk, chunk.size, written)
                    }
                } catch (e: RemoteException) {
                    val io = IOException("RemoteException during HSAE RFCOMM send", e)
                    synchronized(lock) { failure = io }
                    throw io
                }
                if (ret < 0) {
                    val io = IOException("HSAE RFCOMM send returned error: ret=$ret")
                    synchronized(lock) { failure = io }
                    throw io
                }
                val nWritten = if (written[0] > 0) written[0] else chunkSize
                offset += nWritten
            }
        }
    }

    override fun recv(maxBytes: Int, timeoutMillis: Long): ByteArray? {
        require(maxBytes > 0) { "maxBytes must be positive" }
        require(timeoutMillis >= 0) { "timeoutMillis must not be negative" }

        val deadlineNanos = System.nanoTime() + timeoutMillis * 1_000_000L
        synchronized(lock) {
            while (true) {
                takePendingLocked(maxBytes)?.let { return it }
                failure?.let { throw it }
                if (peerEnded || closed) return EMPTY

                val remainingNanos = deadlineNanos - System.nanoTime()
                if (remainingNanos <= 0) return null
                try {
                    lock.wait(remainingNanos / 1_000_000L, (remainingNanos % 1_000_000L).toInt())
                } catch (_: InterruptedException) {
                    Thread.currentThread().interrupt()
                    return null
                }
            }
        }
    }

    private fun takePendingLocked(maxBytes: Int): ByteArray? {
        val first = pending.peekFirst() ?: return null
        return if (first.size <= maxBytes) {
            pending.removeFirst()
            pendingBytes -= first.size
            first
        } else {
            val chunk = first.copyOf(maxBytes)
            pending.removeFirst()
            pending.addFirst(first.copyOfRange(maxBytes, first.size))
            pendingBytes -= maxBytes
            chunk
        }
    }

    override fun close() {
        synchronized(lock) {
            if (closed) return
            closed = true
            lock.notifyAll()
        }
        try {
            if (isSocketMode) {
                if (socketCallback != null) runCatching { service.ANWBT_UnRegistrySocketDataCallback(socketCallback) }
                service.ANWBT_SocketDisconnect(connectionIndex)
            } else {
                if (sppCallback != null) runCatching { service.ANWBT_UnRegistrySPPDataCallback(sppCallback) }
                service.ANWBT_SPPDisconnect(connectionIndex)
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error disconnecting HSAE RFCOMM index=$connectionIndex", e)
        }
        runCatching { context.unbindService(connection) }
    }

    companion object {
        private const val TAG = "HsaeRfcommStream"
        private val EMPTY = ByteArray(0)
        private const val SERVICE_ACTION = "com.anwsdk.service.AnwPhoneLink"
        private const val SERVICE_PACKAGE = "com.anwsdk.service"

        fun connect(context: Context, address: String, uuidString: String): BlockingDuplexByteStream {
            Log.i(TAG, "Connecting HSAE RFCOMM to $address uuid=$uuidString")
            val binderLatch = CountDownLatch(1)
            var phoneLink: IAnwPhoneLink? = null

            val serviceConnection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                    Log.i(TAG, "AnwPhoneLink service connected")
                    phoneLink = IAnwPhoneLink.Stub.asInterface(binder)
                    binderLatch.countDown()
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    Log.w(TAG, "AnwPhoneLink service disconnected")
                    phoneLink = null
                }
            }

            val intent = Intent(SERVICE_ACTION).setPackage(SERVICE_PACKAGE)
            val bound = context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            if (!bound) {
                throw IOException("Could not bind to AnwSdkService ($SERVICE_ACTION)")
            }

            if (!binderLatch.await(5000, TimeUnit.MILLISECONDS)) {
                context.unbindService(serviceConnection)
                throw IOException("Timed out waiting for AnwPhoneLink binding")
            }

            val link = phoneLink ?: run {
                context.unbindService(serviceConnection)
                throw IOException("AnwPhoneLink interface is null after connect")
            }

            val uuidBytes = uuidToBytes(uuidString)
            val guidBytes = uuidToGuidBytes(uuidString)

            var stream: HsaeRfcommStream? = null

            // 1. Try Socket Connect with standard UUID bytes and GUID bytes
            val socketCallback = object : IAnwSocketDataCallBack.Stub() {
                override fun SocketDataIND(nIndex: Int, data: ByteArray?, dataLength: Int) {
                    if (data != null && dataLength > 0) {
                        stream?.onDataReceived(data, dataLength)
                    }
                }
            }

            try {
                link.ANWBT_RegistrySocketDataCallback(socketCallback)
                link.ANWBT_SocketInit()

                val connectIndex = IntArray(1) { -1 }
                // Try GUID format first (Anw native stack)
                var ret = link.ANWBT_SocketConnect(1, address, guidBytes, 0, 0L, 0L, connectIndex)
                Log.i(TAG, "ANWBT_SocketConnect(GUID) returned $ret index=${connectIndex[0]}")

                if (ret < 0 || connectIndex[0] < 0) {
                    // Try RFC 4122 Big-Endian format
                    ret = link.ANWBT_SocketConnect(1, address, uuidBytes, 0, 0L, 0L, connectIndex)
                    Log.i(TAG, "ANWBT_SocketConnect(RFC) returned $ret index=${connectIndex[0]}")
                }

                if (ret >= 0 && connectIndex[0] >= 0) {
                    val s = HsaeRfcommStream(
                        context = context,
                        service = link,
                        connectionIndex = connectIndex[0],
                        isSocketMode = true,
                        connection = serviceConnection,
                        socketCallback = socketCallback,
                        sppCallback = null,
                    )
                    stream = s
                    Log.i(TAG, "HSAE Socket connect successful index=${connectIndex[0]}")
                    return s
                }
            } catch (e: Exception) {
                Log.w(TAG, "SocketConnect failed, falling back to SPP: ${e.message}")
            }

            // 2. Fallback to SPP Connect
            val sppCallback = object : IAnwSPPDataCallBack.Stub() {
                override fun SPPDataIND(nIndex: Int, data: ByteArray?, dataLength: Int) {
                    if (data != null && dataLength > 0) {
                        stream?.onDataReceived(data, dataLength)
                    }
                }
            }

            try {
                link.ANWBT_RegistrySPPDataCallback(sppCallback)
                link.ANWBT_SPPInit(guidBytes)

                val sppIndex = IntArray(1) { -1 }
                var ret = link.ANWBT_SPPConnect(address, guidBytes, sppIndex)
                Log.i(TAG, "ANWBT_SPPConnect(GUID) returned $ret index=${sppIndex[0]}")

                if (ret < 0 || sppIndex[0] < 0) {
                    link.ANWBT_SPPInit(uuidBytes)
                    ret = link.ANWBT_SPPConnect(address, uuidBytes, sppIndex)
                    Log.i(TAG, "ANWBT_SPPConnect(RFC) returned $ret index=${sppIndex[0]}")
                }

                if (ret >= 0 && sppIndex[0] >= 0) {
                    val s = HsaeRfcommStream(
                        context = context,
                        service = link,
                        connectionIndex = sppIndex[0],
                        isSocketMode = false,
                        connection = serviceConnection,
                        socketCallback = null,
                        sppCallback = sppCallback,
                    )
                    stream = s
                    Log.i(TAG, "HSAE SPP connect successful index=${sppIndex[0]}")
                    return s
                }
            } catch (e: Exception) {
                Log.w(TAG, "SPPConnect failed: ${e.message}")
            }

            // Clean up if all attempts failed
            runCatching { context.unbindService(serviceConnection) }
            throw IOException("HSAE Bluetooth RFCOMM connect failed for $address ($uuidString)")
        }

        private fun uuidToBytes(uuidString: String): ByteArray {
            val uuid = UUID.fromString(uuidString)
            val bytes = ByteArray(16)
            val msb = uuid.mostSignificantBits
            val lsb = uuid.leastSignificantBits
            for (i in 0..7) bytes[i] = ((msb ushr (8 * (7 - i))) and 0xFF).toByte()
            for (i in 8..15) bytes[i] = ((lsb ushr (8 * (15 - i))) and 0xFF).toByte()
            return bytes
        }

        private fun uuidToGuidBytes(uuidString: String): ByteArray {
            val raw = uuidToBytes(uuidString)
            val guid = ByteArray(16)
            // Little-endian first 4 bytes
            guid[0] = raw[3]; guid[1] = raw[2]; guid[2] = raw[1]; guid[3] = raw[0]
            // Little-endian next 2 bytes
            guid[4] = raw[5]; guid[5] = raw[4]
            // Little-endian next 2 bytes
            guid[6] = raw[7]; guid[7] = raw[6]
            // Big-endian remaining 8 bytes
            System.arraycopy(raw, 8, guid, 8, 8)
            return guid
        }
    }
}
