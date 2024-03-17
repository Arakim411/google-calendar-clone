package com.arakim.googlecalendarclone.ui.common.calendarrange.model

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class CalendarDayUiModel(
    val dayOfWeek: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
)

fun CalendarDayUiModel.toLocalDate(): LocalDate {
    return LocalDate.of(year, month, dayOfMonth)
}

fun CalendarDayUiModel.isBefore(other: CalendarDayUiModel): Boolean =
    toLocalDate().isBefore(other.toLocalDate())
