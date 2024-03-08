package com.arakim.googlecalendarclone.data.calendarinfo.google

import com.arakim.googlecalendarclone.data.calendarinfo.google.remote.CalendarRemoteDataSource
import com.arakim.googlecalendarclone.data.usertasks.UserTasksRepositoryImpl
import com.arakim.googlecalendarclone.domain.calendarinfo.model.UserCalendarInfo
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.executeCommonNetworkCall
import com.arakim.googlecalendarclone.util.kotlin.getOrThrow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@Singleton
class GoogleCalendarInfoRepositoryImpl @Inject constructor(
    private val calendarRemoteDataSource: CalendarRemoteDataSource,
    private val userTasksRemoteDataSource: UserTasksRepositoryImpl,
) : com.arakim.googlecalendarclone.domain.calendarinfo.UserCalendarRepository {

    override suspend fun getUserCalendarInfo(
        fromDate: LocalDate,
        toDate: LocalDate,
    ): TypedResult<UserCalendarInfo, CommonError> = executeCommonNetworkCall {
        coroutineScope {
            val userEvents = async { calendarRemoteDataSource.getUserEvents(fromDate, toDate) }
            val worldEvents = async { calendarRemoteDataSource.getWorldEvents(fromDate, toDate) }

            val userTasks = userTasksRemoteDataSource.getUserTasks(
                fromDate = fromDate,
                toDate = toDate
            ).getOrThrow()

            com.arakim.googlecalendarclone.domain.calendarinfo.model.UserCalendarInfo(
                userEvents = userEvents.await(),
                worldEvents = worldEvents.await(),
                userTasks = userTasks,
            )
        }
    }
}