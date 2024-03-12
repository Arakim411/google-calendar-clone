package com.arakim.googlecalendarclone.data.calendarinfo.google.remote

import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent.UserEvent
import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent.WorldEvent
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Inject

// TODO handle with "next page token" for pagination, for now we download only max 20 results
class CalendarRemoteDataSource @Inject constructor(
    private val calendarHelper: AccessCalendarHelper,
) {

    suspend fun getUserEvents(
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
            it.toUserEvent()
        }
    }

    suspend fun getWorldEvents(
        minDate: LocalDate,
        maxDateTime: LocalDate,
    ): List<WorldEvent> = calendarHelper.accessCalendar {
        val events = events().list(HOLIDAY_CALENDAR_ID)
            .setTimeMin(minDate.toDateTime())
            .setTimeMax(maxDateTime.toDateTime())
            .setSingleEvents(true)
            .setOrderBy("startTime")
            .execute()

        events.items.map {
            it.toWorldEvent()
        }
    }
}

private const val HOLIDAY_CALENDAR_ID = "en.usa#holiday@group.v.calendar.google.com"

private fun Event.toUserEvent(): UserEvent = UserEvent(
    id = id,
    title = this.summary,
    startDateTime = start.dateTime.toLocalDateTime(),
    endDateTime = end.dateTime.toLocalDateTime(),
    description = description ?: ""
)

private fun Event.toWorldEvent(): WorldEvent = WorldEvent(
    id = id,
    title = this.summary,
    startDateTime = this.start.date.toLocalDateTime(),
    description = description ?: ""
)

private fun DateTime.toLocalDateTime(): LocalDateTime =
    Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime()

private fun LocalDate.toDateTime(): DateTime =
    DateTime(atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli())
