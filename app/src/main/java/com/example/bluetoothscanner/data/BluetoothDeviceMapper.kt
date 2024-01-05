package com.example.bluetoothscanner.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.bluetoothscanner.domain.entity.BluetoothDeviceEntity

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceEntity {
    return BluetoothDeviceEntity(
        name = name,
        address = address
    )
}