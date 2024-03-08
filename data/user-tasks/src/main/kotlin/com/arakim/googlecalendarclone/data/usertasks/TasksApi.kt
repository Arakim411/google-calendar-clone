package com.arakim.googlecalendarclone.data.usertasks

import com.arakim.googlecalendarclone.data.usertasks.model.TaskListsDto
import com.arakim.googlecalendarclone.data.usertasks.model.TasksResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TasksApi {

    @GET("tasks/v1/users/@me/lists")
    suspend fun getTaskLists(): TaskListsDto

    @GET("tasks/v1/lists/{taskListId}/tasks")
    suspend fun getTaskList(
        @Path("taskListId") taskListId: String,
        @Query("dueMin") dueMinRfc3339: String,
        @Query("dueMax") dueMaxRfc3339: String,
    ): TasksResponseDto
}