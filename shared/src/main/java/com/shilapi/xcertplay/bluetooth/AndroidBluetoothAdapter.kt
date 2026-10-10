package com.shilapi.xcertplay.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.provider.Settings
import android.util.Log

/**
 * Standard Android Bluetooth implementation backed by [android.bluetooth.BluetoothAdapter].
 */
@SuppressLint("MissingPermission")
class AndroidBluetoothAdapter(private val context: Context) : CarPlayBluetooth {
    override val adapterName: String = "AndroidBluetoothAdapter"

    private val adapter: BluetoothAdapter?
        get() = runCatching {
            context.getSystemService(BluetoothManager::class.java)?.adapter
                ?: BluetoothAdapter.getDefaultAdapter()
        }.getOrNull()

    override fun isSupported(): Boolean = adapter != null

    override fun isEnabled(): Boolean = runCatching { adapter?.isEnabled == true }.getOrDefault(false)

    override fun enable(): Boolean {
        return runCatching {
            val a = adapter ?: return false
            if (a.isEnabled) return true
            @Suppress("DEPRECATION")
            a.enable()
        }.getOrDefault(false)
    }

    override fun getPairedDevices(): List<CarPlayBluetoothDevice> {
        val a = adapter ?: return emptyList()
        return runCatching {
            a.bondedDevices.orEmpty().map { device ->
                CarPlayBluetoothDevice(
                    name = device.name ?: "Unknown",
                    address = device.address,
                    isConnected = isDeviceConnected(device),
                )
            }
        }.getOrDefault(emptyList())
    }

    override fun getConnectedDevice(): CarPlayBluetoothDevice? {
        val paired = getPairedDevices()
        return paired.firstOrNull { it.isConnected }
    }

    override fun localAddress(context: Context): String? {
        val adapterAddr = runCatching { adapter?.address }.getOrNull()
        val settingAddr = runCatching {
            Settings.Secure.getString(context.contentResolver, "bluetooth_address")
        }.getOrNull()
        return listOfNotNull(adapterAddr, settingAddr).firstOrNull {
            Regex("(?i)([0-9a-f]{2}:){5}[0-9a-f]{2}").matches(it) &&
                !it.startsWith("02:00:00:00:00:") && it != "00:00:00:00:00:00"
        }
    }

    private fun isDeviceConnected(device: BluetoothDevice): Boolean {
        return runCatching {
            val method = BluetoothDevice::class.java.getMethod("isConnected")
            method.invoke(device) as? Boolean == true
        }.getOrDefault(false)
    }

    override fun openRfcommStream(deviceAddress: String, uuid: String): com.shilapi.xcertplay.transport.BlockingDuplexByteStream {
        val a = adapter ?: throw java.io.IOException("Bluetooth adapter is unavailable")
        val device = a.getRemoteDevice(deviceAddress)
        val socket = device.createRfcommSocketToServiceRecord(java.util.UUID.fromString(uuid))
        val result = java.util.concurrent.atomic.AtomicReference<Throwable?>()
        val latch = java.util.concurrent.CountDownLatch(1)
        val thread = Thread({
            try {
                socket.connect()
            } catch (t: Throwable) {
                result.set(t)
            } finally {
                latch.countDown()
            }
        }, "aosp-rfcomm-connect").apply { isDaemon = true; start() }
        val completed = latch.await(10000, java.util.concurrent.TimeUnit.MILLISECONDS)
        if (!completed) {
            runCatching { socket.close() }
            throw java.io.IOException("Timed out connecting AOSP RFCOMM to $deviceAddress")
        }
        val err = result.get()
        if (err != null) {
            runCatching { socket.close() }
            throw if (err is java.io.IOException) err else java.io.IOException("Could not connect RFCOMM", err)
        }
        return com.shilapi.xcertplay.transport.BluetoothRfcommDuplexStream(socket)
    }
}
