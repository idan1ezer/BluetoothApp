package com.example.bluetoothscanner.presentation.device_list

import com.example.bluetoothscanner.domain.entity.BluetoothDeviceEntity

data class BluetoothUiState(
    val scannedDevices: List<BluetoothDeviceEntity> = emptyList(),
    val pairedDevices: List<BluetoothDeviceEntity> = emptyList(),
)