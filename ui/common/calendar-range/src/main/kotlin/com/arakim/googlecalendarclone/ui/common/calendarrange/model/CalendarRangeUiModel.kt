package com.arakim.googlecalendarclone.ui.common.calendarrange.model

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class CalendarRangeUiModel(
    val fromDate: LocalDate,
    val toDate: LocalDate,
    val eventsInfo: CalendarInfoUiModel,
)