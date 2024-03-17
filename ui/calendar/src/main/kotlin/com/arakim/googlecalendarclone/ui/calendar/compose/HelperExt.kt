@file:Suppress("MagicNumber")

package com.arakim.googlecalendarclone.ui.calendar.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent
import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent.UserEvent
import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent.UserTask
import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent.WorldEvent
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.ui.calendar.R
import com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.ScheduleConsts
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
@Stable
fun Int.asShortDay(): String = when (this) {
    1 -> stringResource(id = R.string.short_monday)
    2 -> stringResource(id = R.string.short_tuesday)
    3 -> stringResource(id = R.string.short_wednesday)
    4 -> stringResource(id = R.string.short_thursday)
    5 -> stringResource(id = R.string.short_friday)
    6 -> stringResource(id = R.string.short_saturday)
    7 -> stringResource(id = R.string.short_sunday)
    else -> "Unknown"
}

@Composable
@Stable
fun CalendarEvent.getBackgroundColor(): Color = remember {
    when (this) {
        is UserEvent -> ScheduleConsts.EventColorBackground
        is UserTask -> ScheduleConsts.TaskColorBackground
        is WorldEvent -> ScheduleConsts.HolidayColorBackground
    }
}

@Composable
@Stable
fun CalendarEvent.getTextColor(): Color = remember {
    when (this) {
        is UserEvent -> ScheduleConsts.EventColorText
        is UserTask -> ScheduleConsts.TaskColorText
        is WorldEvent -> ScheduleConsts.HolidayColorText
    }
}

@Composable
@Stable
fun CalendarEvent.getHoursRange(): String? = when (this) {
    is UserEvent -> "${this.startDateTime.getFormattedHours(false)} - ${this.endDateTime.getFormattedHours()}"
    is UserTask -> this.startDateTime.getFormattedHours()
    is WorldEvent -> null
}

private fun LocalDateTime.getFormattedHours(aaIncluded: Boolean = true) =
    DateTimeFormatter.ofPattern("hh:mm ${if (aaIncluded) "a" else ""}").format(this)

@Stable
fun CalendarEvent.shouldShowEvent(setUp: CalendarSetUp): Boolean = when (this) {
    is UserEvent -> setUp.isEventsChecked
    is UserTask -> setUp.isTasksChecked
    is WorldEvent -> setUp.isHolidaysChecked
}
