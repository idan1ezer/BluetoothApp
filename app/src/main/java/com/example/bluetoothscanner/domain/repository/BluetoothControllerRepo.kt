package com.example.bluetoothscanner.domain.repository

import com.example.bluetoothscanner.domain.entity.BluetoothDeviceEntity
import kotlinx.coroutines.flow.StateFlow

//BluetoothController

interface BluetoothControllerRepo {
    val scannedDevices: StateFlow<List<BluetoothDeviceEntity>>
    val pairedDevices: StateFlow<List<BluetoothDeviceEntity>>

    fun startScan()
    fun stopScan()

    fun release()
}