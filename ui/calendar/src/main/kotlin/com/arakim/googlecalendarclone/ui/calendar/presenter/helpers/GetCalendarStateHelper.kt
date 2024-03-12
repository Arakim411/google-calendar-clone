package com.arakim.googlecalendarclone.ui.calendar.presenter.helpers

import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType.Day
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType.Month
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType.Schedule
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType.ThreeDays
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType.Week
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.DayState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.MonthState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.ThreeDaysState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.WeekState
import com.arakim.googlecalendarclone.ui.calendar.presenter.helpers.schedule.GetScheduleUiStateHelper
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarRangeUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.toCalendarDay
import com.arakim.googlecalendarclone.util.compose.ImmutableWrapper
import java.time.LocalDate
import javax.inject.Inject

class GetCalendarStateHelper @Inject constructor(
    private val getScheduleState: GetScheduleUiStateHelper,
) {

    operator fun invoke(
        range: CalendarRangeUiModel,
        setUp: CalendarSetUp,
    ): ReadyState {
        val wrappedSetUp = ImmutableWrapper(setUp)
        val selectedDay = LocalDate.now().toCalendarDay()

        return when (setUp.rangeType) {
            Schedule -> getScheduleState(range, setUp, selectedDay)
            Day -> DayState(range, wrappedSetUp, selectedDay)
            ThreeDays -> ThreeDaysState(range, wrappedSetUp, selectedDay)
            Week -> WeekState(range, wrappedSetUp, selectedDay)
            Month -> MonthState(range, wrappedSetUp, selectedDay)
        }
    }
}