package com.example.bluetoothscanner.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import com.example.bluetoothscanner.data.models.FoundDeviceReceiver
import com.example.bluetoothscanner.data.toBluetoothDeviceDomain
import com.example.bluetoothscanner.domain.entity.BluetoothDeviceEntity
import com.example.bluetoothscanner.domain.repository.BluetoothControllerRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

//AndroidBluetoothController

@SuppressLint("MissingPermission")
class BluetoothControllerRepoImpl(
    private val context: Context
): BluetoothControllerRepo {

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val _scannedDevices  = MutableStateFlow<List<BluetoothDeviceEntity>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceEntity>>
        get() = _scannedDevices.asStateFlow()

    private val _pairedDevices  = MutableStateFlow<List<BluetoothDeviceEntity>>(emptyList())
    override val pairedDevices: StateFlow<List<BluetoothDeviceEntity>>
        get() = _pairedDevices.asStateFlow()

    private val foundDeviceReceiver = FoundDeviceReceiver {device ->
        _scannedDevices.update {devices ->
            val newDevice = device.toBluetoothDeviceDomain()
            if (newDevice in devices) devices else devices + newDevice
        }
    }

    init {
        updatePairedDevices()
    }

    override fun startScan() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(BluetoothDevice.ACTION_FOUND)
        )

        updatePairedDevices()

        bluetoothAdapter?.startDiscovery()
    }

    override fun stopScan() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        bluetoothAdapter?.cancelDiscovery()
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
    }

    @SuppressLint("MissingPermission")
    private fun updatePairedDevices() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }
        bluetoothAdapter
            ?.bondedDevices
            ?.map { it.toBluetoothDeviceDomain() }
            ?.also { devices ->
                _pairedDevices.update { devices }
            }
    }


    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}