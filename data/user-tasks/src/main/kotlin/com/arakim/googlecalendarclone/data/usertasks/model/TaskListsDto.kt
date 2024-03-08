package com.arakim.googlecalendarclone.data.usertasks.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TaskListsDto(
    @Json(name = "items") val items: List<TaskListDto>,
)