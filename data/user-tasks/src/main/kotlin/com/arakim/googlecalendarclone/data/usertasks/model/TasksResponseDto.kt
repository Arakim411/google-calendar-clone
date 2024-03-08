package com.arakim.googlecalendarclone.data.usertasks.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TasksResponseDto(
    @Json(name = "items") val items: List<TaskDto>,
)