@file:Suppress("MagicNumber")
package com.arakim.googlecalendarclone.ui.calendar.compose.stateviews

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object ScheduleConsts {

    val ItemSpacing = 16.dp

    object MonthTitle {
        val Height = 200.dp
        val PaddingStart = 64.dp
        val PaddingTop = 32.dp
    }

    val EventColorBackground = Color(0xFF66AEE9)
    val TaskColorBackground = Color(0xFF7A9CF5)
    val HolidayColorBackground = Color(0xFF53AB9F)

    val EventColorText = Color(0xFF000000)
    val TaskColorText = Color.White
    val HolidayColorText = Color.White
}