package com.arakim.googlecalendarclone.ui.calendar.presenter

import androidx.compose.runtime.Immutable
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarRangeUiModel
import com.arakim.googlecalendarclone.util.kotlin.CommonError

sealed interface CalendarState {
    data object IdleState : CalendarState
    data object InitializingState : CalendarState
    data class ErrorState(val error: CommonError) : CalendarState

    @Immutable
    sealed interface ReadyState : CalendarState {
        val calendarInfo: CalendarRangeUiModel

        @Immutable
        data class ScheduleState(
            override val calendarInfo: CalendarRangeUiModel,
        ) : ReadyState

        @Immutable
        data class DayState(
            override val calendarInfo: CalendarRangeUiModel,
        ) : ReadyState

        @Immutable
        data class Days3State(
            override val calendarInfo: CalendarRangeUiModel,
        ) : ReadyState

        @Immutable
        data class WeekState(
            override val calendarInfo: CalendarRangeUiModel,
        ) : ReadyState

        @Immutable
        data class MonthState(
            override val calendarInfo: CalendarRangeUiModel,
        ) : ReadyState
    }
}