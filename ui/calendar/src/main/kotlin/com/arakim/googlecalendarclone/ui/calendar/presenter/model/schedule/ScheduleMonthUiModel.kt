package com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule

import androidx.compose.runtime.Immutable
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarMonthUiModel

@Immutable
data class ScheduleMonthUiModel(
    val monthValue: Int,
    val year: Int,
    val daysRange: List<ScheduleDayRangeUiModel>,
)

fun ScheduleMonthUiModel.toCalendarMonthUiModel() = CalendarMonthUiModel(
    monthValue = monthValue,
    year = year,
)

fun ScheduleMonthUiModel.isBefore(other: ScheduleMonthUiModel): Boolean {
    return year < other.year || (year == other.year && monthValue < other.monthValue)
}

fun ScheduleMonthUiModel.isAfter(other: ScheduleMonthUiModel): Boolean {
    return year > other.year || (year == other.year && monthValue > other.monthValue)
}