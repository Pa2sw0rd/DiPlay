package com.shilapi.xcertplay.bluetooth

import android.content.Context

/**
 * Unified interface abstracting head unit Bluetooth operations for CarPlay.
 * Enables transparent adaptation between standard Android Bluetooth and proprietary IVI stacks (such as Nissan HSAE).
 */
interface CarPlayBluetooth {
    /** Human-readable name of the underlying adapter for diagnostics. */
    val adapterName: String

    /** Whether this Bluetooth stack is supported / available on the current device. */
    fun isSupported(): Boolean

    /** Whether Bluetooth radio is currently powered on and active. */
    fun isEnabled(): Boolean

    /** Requests to power on Bluetooth radio. Returns true if request was initiated or radio is already on. */
    fun enable(): Boolean

    /** Returns all devices currently paired with the head unit. */
    fun getPairedDevices(): List<CarPlayBluetoothDevice>

    /** Returns the device currently connected (e.g. HFP/A2DP), or null if none. */
    fun getConnectedDevice(): CarPlayBluetoothDevice?

    /** Returns the local Bluetooth MAC address of this head unit, if resolvable. */
    fun localAddress(context: Context): String?

    /**
     * Connects an RFCOMM byte stream to the specified remote device and service UUID.
     * Implementations may use standard AOSP BluetoothSocket or IVI proprietary bridges (e.g. AnwSdk).
     */
    fun openRfcommStream(deviceAddress: String, uuid: String): com.shilapi.xcertplay.transport.BlockingDuplexByteStream
}
