package com.example.bluetoothscanner.di

import android.content.Context
import com.example.bluetoothscanner.data.repository.BluetoothControllerRepoImpl
import com.example.bluetoothscanner.domain.repository.BluetoothControllerRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBluetoothController(@ApplicationContext context: Context): BluetoothControllerRepo {
        return BluetoothControllerRepoImpl(context)
    }


}