package com.arakim.googlecalendarclone.ui.calendar.presenter.helpers.schedule

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.ScheduleState
import com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule.ScheduleMonthUiModel
import com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule.isAfter
import com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule.isBefore
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarDayUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarMonthUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarRangeUiModel
import com.arakim.googlecalendarclone.util.compose.ImmutableList
import com.arakim.googlecalendarclone.util.compose.ImmutableWrapper
import com.arakim.googlecalendarclone.util.compose.toImmutableList
import java.time.LocalDate
import javax.inject.Inject

class GetScheduleUiStateHelper @Inject constructor(
    private val getScheduleDaysRange: GetScheduleDayRangesHelper,
) {

    operator fun invoke(
        range: CalendarRangeUiModel,
        setUp: CalendarSetUp,
        selectedDay: State<CalendarDayUiModel>,
    ): ScheduleState {
        val months = getScheduleMonths(range.fromDate, range.toDate, range)
        val initialMonth = months.getInitialMonth()

        val after: ImmutableList<ScheduleMonthUiModel> =
            months.filter { it.isAfter(initialMonth) }.toImmutableList()
        val before: ImmutableList<ScheduleMonthUiModel> =
            months.filter { it.isBefore(initialMonth) }.reversed().toImmutableList()

        return ScheduleState(
            calendarInfo = range,
            calendarSetUp = mutableStateOf(ImmutableWrapper(setUp)),
            selectedDay = selectedDay,
            initialMonth = initialMonth,
            monthsAfterInitial = after,
            monthsBeforeInitial = before,
        )
    }

    private fun getScheduleMonths(
        from: LocalDate,
        to: LocalDate,
        range: CalendarRangeUiModel,
    ): ImmutableList<ScheduleMonthUiModel> {
        val list = mutableListOf<ScheduleMonthUiModel>()
        forEachMonth(from, to) { month ->
            val dayRanges = getScheduleDaysRange(month, range)
            list.add(ScheduleMonthUiModel(month.monthValue, month.year, dayRanges))
        }
        return list.toImmutableList()
    }

    private inline fun forEachMonth(
        from: LocalDate,
        to: LocalDate,
        unit: (CalendarMonthUiModel) -> Unit,
    ) {
        var current = from
        while (current <= to) {
            unit(CalendarMonthUiModel(current.monthValue, current.year))
            current = current.plusMonths(1)
        }
    }

    private fun ImmutableList<ScheduleMonthUiModel>.getInitialMonth(): ScheduleMonthUiModel {
        val today = LocalDate.now()
        return find { it.monthValue == today.monthValue && it.year == today.year } ?: first()
    }
}