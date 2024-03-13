package com.arakim.googlecalendarclone.ui.common.calendarrange.model

import androidx.compose.runtime.Immutable

@Immutable
data class CalendarDayUiModel(
    val dayOfWeek: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
)