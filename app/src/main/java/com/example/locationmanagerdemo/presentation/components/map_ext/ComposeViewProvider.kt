package com.example.locationmanagerdemo.presentation.components.map_ext

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.yandex.runtime.ui_view.ViewProvider

class ComposeViewProvider(
    private val composeView: AbstractComposeView,
    private val compositionContext: CompositionContext,
    private val container: View?
): ViewProvider(composeView) {

    init {
        composeView.setParentCompositionContext(compositionContext)
        snapshot()
    }

    override fun snapshot() {
        if (container == null) return
        (container as ViewGroup).addView(composeView)
        try {
            super.snapshot()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        container.removeView(composeView)
    }
}

@SuppressLint("ComposableNaming")
@Composable
fun ComposeViewProvider(
    content: @Composable () -> Unit
): ComposeViewProvider {
    val context = LocalContext.current
    val localView = LocalView.current
    val compositionContext = rememberCompositionContext()
    return ComposeViewProvider(
        composeView = ComposeView(context) {
            content()
        },
        compositionContext = compositionContext,
        container = localView
    )
}


fun ComposeView(context: Context, content: @Composable () -> Unit) =
    androidx.compose.ui.platform.ComposeView(context).apply {
        setContent(content)
    }