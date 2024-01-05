package com.example.bluetoothscanner.domain.entity

typealias BluetoothDeviceEntity = BluetoothDevice

data class BluetoothDevice(
    val name: String?,
    val address: String
)