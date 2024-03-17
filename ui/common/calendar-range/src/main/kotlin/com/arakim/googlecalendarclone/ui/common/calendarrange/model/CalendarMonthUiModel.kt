package com.arakim.googlecalendarclone.ui.common.calendarrange.model

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class CalendarMonthUiModel(
    val monthValue: Int,
    val year: Int,
)

fun CalendarMonthUiModel.toCalendarDay(): CalendarDayUiModel {
    val localDate = LocalDate.of(year, monthValue, 1)
    return CalendarDayUiModel(
        dayOfWeek = localDate.dayOfWeek.value,
        dayOfMonth = localDate.dayOfMonth,
        month = localDate.monthValue,
        year = localDate.year,
    )
}