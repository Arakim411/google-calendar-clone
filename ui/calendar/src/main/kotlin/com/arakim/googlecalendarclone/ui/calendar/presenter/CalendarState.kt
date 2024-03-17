package com.arakim.googlecalendarclone.ui.calendar.presenter

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
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
        val calendarSetUp: State<ImmutableWrapper<CalendarSetUp>>
        val selectedDay: State<CalendarDayUiModel>

        @Immutable
        data class ScheduleState(
            override val calendarInfo: CalendarRangeUiModel,
            override val calendarSetUp: State<ImmutableWrapper<CalendarSetUp>>,
            override val selectedDay: State<CalendarDayUiModel>,
            val initialMonth: ScheduleMonthUiModel,
            val monthsAfterInitial: ImmutableList<ScheduleMonthUiModel>,
            val monthsBeforeInitial: ImmutableList<ScheduleMonthUiModel>,
        ) : ReadyState

        @Immutable
        data class DayState(
            override val calendarInfo: CalendarRangeUiModel,
            override val calendarSetUp: State<ImmutableWrapper<CalendarSetUp>>,
            override val selectedDay: State<CalendarDayUiModel>,
        ) : ReadyState

        @Immutable
        data class ThreeDaysState(
            override val calendarInfo: CalendarRangeUiModel,
            override val calendarSetUp: State<ImmutableWrapper<CalendarSetUp>>,
            override val selectedDay: State<CalendarDayUiModel>,
        ) : ReadyState

        @Immutable
        data class WeekState(
            override val calendarInfo: CalendarRangeUiModel,
            override val calendarSetUp: State<ImmutableWrapper<CalendarSetUp>>,
            override val selectedDay: State<CalendarDayUiModel>,
        ) : ReadyState

        @Immutable
        data class MonthState(
            override val calendarInfo: CalendarRangeUiModel,
            override val calendarSetUp: State<ImmutableWrapper<CalendarSetUp>>,
            override val selectedDay: State<CalendarDayUiModel>,
        ) : ReadyState
    }
}