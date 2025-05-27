package com.example.reserved.data.remote.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

object LocationUtils {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    fun init(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {
        return try {
            fusedLocationClient?.lastLocation?.await()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}