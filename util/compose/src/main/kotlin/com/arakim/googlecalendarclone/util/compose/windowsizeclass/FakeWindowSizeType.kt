package com.arakim.googlecalendarclone.util.compose.windowsizeclass

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/*
do not use in production code, this are helpers for @Preview, tests...
 */

@Composable
fun FakeWindowSizeType(
    width: WindowSizeType = WindowSizeType.Medium,
    height: WindowSizeType = WindowSizeType.Medium,
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(LocalWindowSize provides WindowSize(width, height)) {
        content()
    }
}