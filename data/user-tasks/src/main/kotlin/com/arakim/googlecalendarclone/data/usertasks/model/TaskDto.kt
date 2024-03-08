package com.arakim.googlecalendarclone.data.usertasks.model

import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent.UserTask
import com.arakim.googlecalendarclone.util.kotlin.toLocalDateTime
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TaskDto(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "notes") val description: String?,
    @Json(name = "due") val due: String,
)

internal fun TaskDto.toDomain(): UserTask = UserTask(
    title = title,
    id = id,
    startDateTime = due.toLocalDateTime(),
    description = description ?: ""
)