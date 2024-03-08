package com.arakim.googlecalendarclone.data.usertasks

import com.arakim.googlecalendarclone.data.usertasks.model.toDomain
import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent.UserTask
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.executeCommonNetworkCall
import com.arakim.googlecalendarclone.util.kotlin.toRfc3399
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

// TODO handle with "next page token" for pagination, for now we download only max 20 results
class UserTasksRepositoryImpl @Inject constructor(
    private val api: TasksApi,
) {

    suspend fun getUserTasks(
        fromDate: LocalDate,
        toDate: LocalDate,
    ): TypedResult<List<UserTask>, CommonError> = executeCommonNetworkCall {
        coroutineScope {
            val lists = api.getTaskLists()
            val taskLists = lists.items.map { list ->
                async {
                    api.getTaskList(
                        taskListId = list.id,
                        dueMinRfc3339 = fromDate.toRfc3399(),
                        dueMaxRfc3339 = toDate.toRfc3399(),
                    )
                }
            }
            val tasks = taskLists.awaitAll().flatMap { it.items }
            tasks.map { it.toDomain() }
        }
    }
}