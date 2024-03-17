package com.arakim.googlecalendarclone.ui.common.calendarrange.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.arakim.googlecalendarclone.ui.common.calendarrange.Day
import com.arakim.googlecalendarclone.ui.common.calendarrange.Event
import com.arakim.googlecalendarclone.ui.common.calendarrange.Month

@Immutable
data class CalendarInfoUiModel(val monthsToEvents: Map<Month, HashMap<Day, List<Event>>>)

@Stable
fun CalendarInfoUiModel.getEventInDay(day: Day): List<Event>? =
    monthsToEvents[day.toMonth()]?.get(day)

@Stable
fun CalendarInfoUiModel.getEventsInMonth(month: Month): List<Event>? =
    monthsToEvents[month]?.values?.flatten()

@Stable
fun CalendarInfoUiModel.getEventsInRange(
    month: Month,
    fromDay: Int,
    toDay: Int,
): Map<Day, List<Event>> {
    return monthsToEvents[month]?.filter { it.key.dayOfMonth in fromDay..toDay } ?: emptyMap()
}

fun Day.toMonth(): Month = Month(this.month, this.year)