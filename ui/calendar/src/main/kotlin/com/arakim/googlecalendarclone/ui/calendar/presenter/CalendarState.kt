package com.arakim.googlecalendarclone.ui.calendar.presenter

import androidx.compose.runtime.Immutable
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule.ScheduleMonthUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarDayUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarRangeUiModel
import com.arakim.googlecalendarclone.util.compose.ImmutableList
import com.arakim.googlecalendarclone.util.compose.ImmutableWrapper
import com.arakim.googlecalendarclone.util.kotlin.CommonError

sealed interface CalendarState {
    data object IdleState : CalendarState
    data object InitializingState : CalendarState
    data class ErrorState(val error: CommonError) : CalendarState

    @Immutable
    sealed interface ReadyState : CalendarState {
        val calendarInfo: CalendarRangeUiModel
        val calendarSetUp: ImmutableWrapper<CalendarSetUp>
        val selectedDay: CalendarDayUiModel

        @Immutable
        data class ScheduleState(
            override val calendarInfo: CalendarRangeUiModel,
            override val calendarSetUp: ImmutableWrapper<CalendarSetUp>,
            override val selectedDay: CalendarDayUiModel,
            val scheduleMonths: ImmutableList<ScheduleMonthUiModel>
        ) : ReadyState

        @Immutable
        data class DayState(
            override val calendarInfo: CalendarRangeUiModel,
            override val calendarSetUp: ImmutableWrapper<CalendarSetUp>,
            override val selectedDay: CalendarDayUiModel,
        ) : ReadyState

        @Immutable
        data class ThreeDaysState(
            override val calendarInfo: CalendarRangeUiModel,
            override val calendarSetUp: ImmutableWrapper<CalendarSetUp>,
            override val selectedDay: CalendarDayUiModel,
        ) : ReadyState

        @Immutable
        data class WeekState(
            override val calendarInfo: CalendarRangeUiModel,
            override val calendarSetUp: ImmutableWrapper<CalendarSetUp>,
            override val selectedDay: CalendarDayUiModel,
        ) : ReadyState

        @Immutable
        data class MonthState(
            override val calendarInfo: CalendarRangeUiModel,
            override val calendarSetUp: ImmutableWrapper<CalendarSetUp>,
            override val selectedDay: CalendarDayUiModel,
        ) : ReadyState
    }
}