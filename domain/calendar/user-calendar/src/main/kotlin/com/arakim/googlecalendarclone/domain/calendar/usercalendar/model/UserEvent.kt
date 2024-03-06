package com.arakim.googlecalendarclone.domain.calendar.usercalendar.model

import java.time.LocalDateTime

data class UserEvent(
    val name: String,
    val id: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
)