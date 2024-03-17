package com.arakim.googlecalendarclone.ui.calendar.presenter

import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarDayUiModel

sealed interface CalendarSideEffect {

    data class SelectedDayChanged(
        val day: CalendarDayUiModel,
    ) : CalendarSideEffect

    data class ScrollToDaySideEffect(val calendarDayUiModel: CalendarDayUiModel) : CalendarSideEffect
}