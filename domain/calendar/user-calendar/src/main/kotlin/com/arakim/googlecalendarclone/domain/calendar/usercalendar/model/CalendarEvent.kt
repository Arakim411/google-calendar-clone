package com.arakim.googlecalendarclone.domain.calendar.usercalendar.model

import java.time.LocalDateTime

sealed interface CalendarEvent {
    val id: String
    val title: String
    val description: String
    val startDateTime: LocalDateTime

    data class UserEvent(
        override val id: String,
        override val title: String,
        override val description: String,
        override val startDateTime: LocalDateTime,
        val endDateTime: LocalDateTime,
    ) : CalendarEvent

    data class UserTask(
        override val id: String,
        override val title: String,
        override val description: String,
        override val startDateTime: LocalDateTime,
    ) : CalendarEvent

    data class WorldEvent(
        override val id: String,
        override val title: String,
        override val description: String,
        override val startDateTime: LocalDateTime,
    ) : CalendarEvent
}