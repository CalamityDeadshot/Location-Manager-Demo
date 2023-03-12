package com.example.locationmanagerdemo

import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.locationmanagerdemo.presentation.components.Map
import com.example.locationmanagerdemo.presentation.theme.LocationManagerDemoTheme
import com.example.locationmanagerdemo.presentation.viewmodel.LocationViewModel
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import java.util.*

class MainActivity : ComponentActivity() {

    val locationViewModel: LocationViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val geocoder = Geocoder(this, Locale.forLanguageTag("ru"))

        MapKitFactory.initialize(this)
        setContent {
            val location by locationViewModel.locationFlow.collectAsState()
            
            val geocoderResults by remember(location) {
                mutableStateOf(
                    geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        5
                    )?.toList()
                )
            }
            
            LocationManagerDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Map(
                            modifier = Modifier.weight(3f),
                            userLocation = location
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            geocoderResults?.forEach { 
                                Text(text = it.getAddressLine(0))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
    }
}