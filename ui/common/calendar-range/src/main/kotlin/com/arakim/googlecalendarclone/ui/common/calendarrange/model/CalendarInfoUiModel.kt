package com.arakim.googlecalendarclone.ui.common.calendarrange.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.arakim.googlecalendarclone.ui.common.calendarrange.Day
import com.arakim.googlecalendarclone.ui.common.calendarrange.Event
import com.arakim.googlecalendarclone.ui.common.calendarrange.Month

@Immutable
data class CalendarInfoUiModel(val monthsToEvents: HashMap<Month, HashMap<Day, List<Event>>>)

@Stable
fun CalendarInfoUiModel.getEventInDay(day: Day): List<Event>? =
    monthsToEvents[day.toMonth()]?.get(day)

@Stable
fun CalendarInfoUiModel.getEventsInMonth(month: Month): List<Event>? =
    monthsToEvents[month]?.values?.flatten()

fun Day.toMonth(): Month = Month(this.month, this.year)