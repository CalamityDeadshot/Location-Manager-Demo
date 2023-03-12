package com.example.locationmanagerdemo.presentation.components

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.locationmanagerdemo.presentation.components.map_ext.ComposeView
import com.example.locationmanagerdemo.presentation.components.map_ext.ComposeViewProvider
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider

@Composable
fun Map(
    userLocation: Point,
    modifier: Modifier = Modifier
) {

    AndroidView(
        modifier = modifier,
        factory = {
            MapView(it)
        },
        update = {
            it.map.mapObjects.clear()
            it.map.mapObjects.addPlacemark(
                userLocation
            )
        }
    )
}