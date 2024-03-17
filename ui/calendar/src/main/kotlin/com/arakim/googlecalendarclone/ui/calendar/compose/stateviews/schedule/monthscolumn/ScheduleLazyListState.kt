package com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.schedule.monthscolumn

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarMonthUiModel

interface ScheduleLazyListState {
    @Stable
    val firstVisibleMonth: State<CalendarMonthUiModel?>
}