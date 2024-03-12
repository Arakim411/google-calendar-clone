package com.arakim.googlecalendarclone.ui.common.calendarrange.model

import androidx.compose.runtime.Immutable

@Immutable
data class CalendarMonthUiModel(
    val monthValue: Int,
    val year: Int,
)