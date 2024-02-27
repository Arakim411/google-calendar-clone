package com.arakim.googlecalendarclone.util.compose.windowsizeclass

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalWindowSize = staticCompositionLocalOf<WindowSize> {
    throw IllegalStateException("WindowSize not initialized")
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun Activity.WindowSizeType(content: @Composable () -> Unit) {

    val windowSizeClass = calculateWindowSizeClass(this).toWindowSize()
    CompositionLocalProvider(LocalWindowSize provides windowSizeClass) {
        content()
    }
}

private fun WindowSizeClass.toWindowSize(): WindowSize = WindowSize(
    width = widthSizeClass.toWindowSizeType(),
    height = heightSizeClass.toWindowSizeType()
)

private fun WindowHeightSizeClass.toWindowSizeType(): WindowSizeType =
    when (this) {
        WindowHeightSizeClass.Compact -> WindowSizeType.Compact
        WindowHeightSizeClass.Medium -> WindowSizeType.Medium
        WindowHeightSizeClass.Expanded -> WindowSizeType.Expanded
        else -> throw IllegalArgumentException("Unknown WindowHeightSizeClass: $this")
    }

private fun WindowWidthSizeClass.toWindowSizeType(): WindowSizeType {
    return when (this) {
        WindowWidthSizeClass.Compact -> WindowSizeType.Compact
        WindowWidthSizeClass.Medium -> WindowSizeType.Medium
        WindowWidthSizeClass.Expanded -> WindowSizeType.Expanded
        else -> throw IllegalArgumentException("Unknown WindowWidthSizeClass: $this")
    }
}