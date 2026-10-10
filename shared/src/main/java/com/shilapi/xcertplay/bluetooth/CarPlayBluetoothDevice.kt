package com.shilapi.xcertplay.bluetooth

/**
 * Represents a Bluetooth device paired with or connected to the head unit.
 */
data class CarPlayBluetoothDevice(
    val name: String,
    val address: String,
    val isConnected: Boolean = false,
)
