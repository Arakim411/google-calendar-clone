@file:Suppress("MagicNumber")

package com.arakim.googlecalendarclone.util.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalContext
import com.arakim.googlecalendarclone.util.compose.R.string

@Suppress("complexity")
fun Int.asMonthString(context: Context): String = with(context.resources) {
    when (this@asMonthString) {
        1 -> getString(string.january)
        2 -> getString(string.february)
        3 -> getString(string.march)
        4 -> getString(string.april)
        5 -> getString(string.may)
        6 -> getString(string.june)
        7 -> getString(string.july)
        8 -> getString(string.august)
        9 -> getString(string.september)
        10 -> getString(string.october)
        11 -> getString(string.november)
        12 -> getString(string.december)
        else -> "Unknown"
    }
}

@Composable
@Stable
fun Int.asMonthString(): String {
    val context = LocalContext.current
    return asMonthString(context)
}