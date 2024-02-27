package com.arakim.googlecalendarclone.util.compose.windowsizeclass

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
data class WindowSize(val width: WindowSizeType, val height: WindowSizeType)

@Immutable
enum class WindowSizeType {
    Compact,
    Medium,
    Expanded,
}

@Immutable
enum class ImpactProperty {
    Height,
    Width,
}

@Composable
fun WindowSizeDelimiter(
    impactedProperty: ImpactProperty,
    content: @Composable (WindowSizeType) -> Unit,
) {
    val windowClassSize = LocalWindowSize.current

    val windowSize = when (impactedProperty) {
        ImpactProperty.Height -> windowClassSize.height
        ImpactProperty.Width -> windowClassSize.width
    }

    content(windowSize)
}