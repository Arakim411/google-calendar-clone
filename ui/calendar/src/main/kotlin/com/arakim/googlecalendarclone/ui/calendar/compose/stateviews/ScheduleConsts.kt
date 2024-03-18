@file:Suppress("MagicNumber")

package com.arakim.googlecalendarclone.ui.calendar.compose.stateviews

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object ScheduleConsts {

    val ItemSpacing = 16.dp

    object MonthTitle {
        val Height = 200.dp
        val PaddingStart = 48.dp
        val PaddingTop = 32.dp
    }

    val EventColorBackground = Color(0xFF66AEE9)
    val TaskColorBackground = Color(0xFF7A9CF5)
    val HolidayColorBackground = Color(0xFF53AB9F)

    val EventColorText = Color(0xFF000000)
    val TaskColorText = Color.White
    val HolidayColorText = Color.White

    val springColors1 = listOf(
        Color(0xFFFFD700),
        Color(0xFF8BC34A),
        Color(0xFF00FF00),
    )

    val springColors2 = listOf(
        Color(0xFF87CEEB),
        Color(0xFFFF6347),
        Color(0xFFFFA07A),
        Color(0xFF00FA9A)
    )

    val winterColors1 = listOf(
        Color(0xFF66AEE9),
        Color(0xFF3F88C5),
        Color(0xFF235B8E),
    )

    val winterColors2 = listOf(
        Color(0xFFFFFFFF),
        Color(0xFFD3D3D3),
        Color(0xFFA9A9A9),
        Color(0xFF696969),
    )

    val summerColors1 = listOf(
        Color(0xFFFF4500),
        Color(0xFFFFD700),
    )

    val summerColors2 = listOf(
        Color(0xFFFF6347),
        Color(0xFFFFA500),
    )

    val autumnColors1 = listOf(
        Color(0xFF8B4513),
        Color(0xFFA0522D),
        Color(0xFFCD853F),
    )

    val autumnColors2 = listOf(
        Color(0xFFDEB887),
        Color(0xFFBC8F8F),
    )
}