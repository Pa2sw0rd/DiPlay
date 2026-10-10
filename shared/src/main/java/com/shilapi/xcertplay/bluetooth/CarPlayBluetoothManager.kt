package com.shilapi.xcertplay.bluetooth

import android.content.Context
import android.util.Log

/**
 * Transparent hybrid Bluetooth adapter combining HSAE IVI Bluetooth service and Android BluetoothAdapter.
 * On Nissan HSAE vehicles, prioritizes HSAE for pairing and power status.
 * On standard Android devices, transparently delegates to Android BluetoothAdapter.
 */
class HybridBluetoothAdapter(
    val hsae: HsaeBluetoothAdapter,
    val android: AndroidBluetoothAdapter,
) : CarPlayBluetooth {
    override val adapterName: String = if (hsae.isSupported()) "Hybrid(HSAE+Android)" else "AndroidBluetoothAdapter"

    override fun isSupported(): Boolean = hsae.isSupported() || android.isSupported()

    override fun isEnabled(): Boolean {
        if (hsae.isSupported() && hsae.isEnabled()) return true
        return android.isEnabled()
    }

    override fun enable(): Boolean {
        var ok = false
        if (hsae.isSupported()) {
            ok = hsae.enable() || ok
        }
        ok = android.enable() || ok
        return ok
    }

    override fun getPairedDevices(): List<CarPlayBluetoothDevice> {
        if (hsae.isSupported()) {
            val hsaeList = hsae.getPairedDevices()
            if (hsaeList.isNotEmpty()) return hsaeList
        }
        return android.getPairedDevices()
    }

    override fun getConnectedDevice(): CarPlayBluetoothDevice? {
        if (hsae.isSupported()) {
            val hsaeConn = hsae.getConnectedDevice()
            if (hsaeConn != null) return hsaeConn
        }
        return android.getConnectedDevice()
    }

    override fun localAddress(context: Context): String? {
        return hsae.localAddress(context) ?: android.localAddress(context)
    }

    override fun openRfcommStream(deviceAddress: String, uuid: String): com.shilapi.xcertplay.transport.BlockingDuplexByteStream {
        if (hsae.isSupported()) {
            return runCatching {
                hsae.openRfcommStream(deviceAddress, uuid)
            }.getOrElse { hsaeError ->
                Log.w("HybridBluetoothAdapter", "HSAE RFCOMM stream failed, trying Android", hsaeError)
                if (android.isSupported() && android.isEnabled()) {
                    android.openRfcommStream(deviceAddress, uuid)
                } else {
                    throw hsaeError
                }
            }
        }
        return android.openRfcommStream(deviceAddress, uuid)
    }
}

/**
 * Entry point for obtaining the appropriate [CarPlayBluetooth] instance.
 */
object CarPlayBluetoothManager {
    private const val TAG = "CarPlayBluetoothManager"

    @Volatile
    private var instance: HybridBluetoothAdapter? = null

    fun get(context: Context): HybridBluetoothAdapter {
        return instance ?: synchronized(this) {
            instance ?: create(context.applicationContext).also { instance = it }
        }
    }

    private fun create(appContext: Context): HybridBluetoothAdapter {
        val hsae = HsaeBluetoothAdapter(appContext)
        val android = AndroidBluetoothAdapter(appContext)
        Log.i(TAG, "Initializing CarPlayBluetoothManager: hsaeSupported=${hsae.isSupported()}, androidSupported=${android.isSupported()}")
        return HybridBluetoothAdapter(hsae, android)
    }
}
