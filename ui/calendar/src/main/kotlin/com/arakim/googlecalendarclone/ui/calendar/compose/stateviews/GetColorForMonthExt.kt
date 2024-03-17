@file:Suppress("MagicNumber")
package com.arakim.googlecalendarclone.ui.calendar.compose.stateviews

import androidx.compose.ui.graphics.Color

@Suppress("complexity")
fun Int.getColorsForMonth(): List<Color> = with(ScheduleConsts) {
    when (this@getColorsForMonth) {
        1 -> listOf(winterColors1.random(), winterColors2.random())
        2 -> listOf(winterColors1.random(), winterColors2.random())
        3 -> listOf(springColors1.random(), springColors2.random())
        4 -> listOf(springColors1.random(), springColors2.random())
        5 -> listOf(springColors1.random(), springColors2.random())
        6 -> listOf(summerColors1.random(), summerColors2.random())
        7 -> listOf(summerColors1.random(), summerColors2.random())
        8 -> listOf(summerColors1.random(), summerColors2.random())
        9 -> listOf(autumnColors1.random(), autumnColors2.random())
        10 -> listOf(autumnColors1.random(), autumnColors2.random())
        11 -> listOf(autumnColors1.random(), autumnColors2.random())
        12 -> listOf(winterColors1.random(), winterColors2.random())
        else -> error("month: $this is not valid")
    }
}