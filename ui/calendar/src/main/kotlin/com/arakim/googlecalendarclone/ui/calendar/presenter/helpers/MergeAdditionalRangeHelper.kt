package com.arakim.googlecalendarclone.ui.calendar.presenter.helpers

import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarInfoUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarRangeUiModel
import javax.inject.Inject

class MergeAdditionalRangeHelper @Inject constructor(
    private val getCalendarStateHelper: GetCalendarStateHelper,
) {

    fun ReadyState.getStateWithMergedRange(range: CalendarRangeUiModel): ReadyState {
        val newFromDate = minOf(calendarInfo.fromDate, range.fromDate)
        val newToDate = maxOf(calendarInfo.toDate, range.toDate)

        val newRange = calendarInfo.copy(
            fromDate = newFromDate,
            toDate = newToDate,
            eventsInfo = CalendarInfoUiModel(calendarInfo.eventsInfo.monthsToEvents + range.eventsInfo.monthsToEvents)
        )

        return getCalendarStateHelper.invoke(newRange, calendarSetUp.value.value, selectedDay)
    }
}