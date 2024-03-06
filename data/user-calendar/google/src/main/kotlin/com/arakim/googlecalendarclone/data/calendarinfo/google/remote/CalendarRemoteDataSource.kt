package com.arakim.googlecalendarclone.data.calendarinfo.google.remote

import com.arakim.googlecalendarclone.domain.calendar.usercalendar.model.UserEvent
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Inject

class CalendarRemoteDataSource @Inject constructor(
    private val calendarHelper: AccessCalendarHelper,
) {

    suspend fun getEvents(
        minDate: LocalDate,
        maxDateTime: LocalDate,
    ): List<UserEvent> = calendarHelper.accessCalendar {

        val events = events().list("primary")
            .setTimeMin(minDate.toDateTime())
            .setTimeMax(maxDateTime.toDateTime())
            .setSingleEvents(true)
            .setOrderBy("startTime")
            .execute()

        events.items.map {
            it.toDomain()
        }
    }
}

private fun Event.toDomain(): UserEvent = UserEvent(
    id = id,
    name = this.summary,
    startDateTime = start.dateTime.toLocalDateTime(),
    endDateTime = end.dateTime.toLocalDateTime(),
)

private fun DateTime.toLocalDateTime(): LocalDateTime =
    Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime()

private fun LocalDate.toDateTime(): DateTime =
    DateTime(atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli())
