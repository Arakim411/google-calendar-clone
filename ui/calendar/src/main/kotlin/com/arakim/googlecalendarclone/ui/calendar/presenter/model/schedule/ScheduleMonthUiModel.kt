package com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule

import androidx.compose.runtime.Immutable

@Immutable
data class ScheduleMonthUiModel(
    val monthValue: Int,
    val year: Int,
    val daysRange: List<ScheduleDayRangeUiModel>,
)