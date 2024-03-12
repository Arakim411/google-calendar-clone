package com.arakim.googlecalendarclone.ui.calendar.presenter.helpers.schedule

import com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule.ScheduleDayRangeUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarMonthUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarRangeUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.getEventsInRange
import com.arakim.googlecalendarclone.util.compose.ImmutableList
import com.arakim.googlecalendarclone.util.compose.ImmutableWrapper
import com.arakim.googlecalendarclone.util.compose.toImmutableList
import java.time.LocalDate
import javax.inject.Inject

class GetScheduleDayRangesHelper @Inject constructor() {

    operator fun invoke(
        month: CalendarMonthUiModel,
        range: CalendarRangeUiModel,
    ): ImmutableList<ScheduleDayRangeUiModel> {
        val result = mutableListOf<ScheduleDayRangeUiModel>()
        val showYear = month.year != LocalDate.now().year

        forEachDayRange(month) { startDay, endDay ->
            val events = range.eventsInfo.getEventsInRange(
                month = month,
                fromDay = startDay,
                toDay = endDay
            )
            result.add(
                ScheduleDayRangeUiModel(
                    startDay = startDay,
                    endDay = endDay,
                    events = ImmutableWrapper(events),
                    showYear = showYear
                )
            )
        }

        return result.toImmutableList()
    }

    private inline fun forEachDayRange(
        month: CalendarMonthUiModel,
        unit: (start: Int, end: Int) -> Unit,
    ) {
        var currentDay = LocalDate.of(month.year, month.monthValue, 1)
        while (currentDay.month.value == month.monthValue) {
            val startDay = currentDay.dayOfMonth
            val endDay = currentDay.plusDaysOrLastInMonth(DaysRangeSize)

            unit(startDay, endDay)
            currentDay = currentDay.plusDays(DaysRangeSize)
        }
    }

    private fun LocalDate.plusDaysOrLastInMonth(days: Long): Int = plusDays(days).let {
        if (this.month != it.month) lengthOfMonth() else it.dayOfMonth
    }

    companion object {
        private const val DaysRangeSize = 7L
    }
}