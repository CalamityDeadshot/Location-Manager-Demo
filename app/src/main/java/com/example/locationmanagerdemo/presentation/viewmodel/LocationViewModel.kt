package com.example.locationmanagerdemo.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import java.util.function.Consumer

class LocationViewModel(
    application: Application
): AndroidViewModel(application) {
    private val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private var activeProvider = LocationManager.FUSED_PROVIDER

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("MissingPermission")
    val locationFlow = callbackFlow<Point> {
        val locationListener = LocationListener { location ->
            Log.v("Location", location.toString())
            trySend(Point(location.latitude, location.longitude))
        }
        locationManager.requestLocationUpdates(
            activeProvider,
            1000,
            1f,
            locationListener
        )
        awaitClose { locationManager.removeUpdates(locationListener) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Point(0.0, 0.0))

    @SuppressLint("MissingPermission")
    fun readLocations() {
        val locationListener = LocationListener { location ->
            Log.v("Location", location.toString())
        }
        locationManager.requestLocationUpdates(
            activeProvider,
            1000,
            1f,
            locationListener
        )
    }
}