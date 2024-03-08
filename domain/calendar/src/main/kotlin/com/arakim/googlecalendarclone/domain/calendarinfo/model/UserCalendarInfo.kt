package com.arakim.googlecalendarclone.domain.calendarinfo.model

import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent.UserEvent
import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent.UserTask
import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent.WorldEvent

data class UserCalendarInfo(
    val userEvents: List<UserEvent>,
    val worldEvents: List<WorldEvent>,
    val userTasks: List<UserTask>,
)