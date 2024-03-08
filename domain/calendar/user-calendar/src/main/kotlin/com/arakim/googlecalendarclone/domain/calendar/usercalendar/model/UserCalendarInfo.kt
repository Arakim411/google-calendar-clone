package com.arakim.googlecalendarclone.domain.calendar.usercalendar.model

import com.arakim.googlecalendarclone.domain.calendar.usercalendar.model.CalendarEvent.UserEvent
import com.arakim.googlecalendarclone.domain.calendar.usercalendar.model.CalendarEvent.UserTask
import com.arakim.googlecalendarclone.domain.calendar.usercalendar.model.CalendarEvent.WorldEvent

data class UserCalendarInfo(
    val userEvents: List<UserEvent>,
    val worldEvents: List<WorldEvent>,
    val userTasks: List<UserTask>,
)