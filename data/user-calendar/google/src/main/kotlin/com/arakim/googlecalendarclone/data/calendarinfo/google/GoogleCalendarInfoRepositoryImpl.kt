package com.arakim.googlecalendarclone.data.calendarinfo.google

import com.arakim.googlecalendarclone.data.calendarinfo.google.remote.CalendarRemoteDataSource
import com.arakim.googlecalendarclone.domain.calendar.usercalendar.UserCalendarRepository
import com.arakim.googlecalendarclone.domain.calendar.usercalendar.model.UserCalendarInfo
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.executeCommonNetworkCall
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@Singleton
class GoogleCalendarInfoRepositoryImpl @Inject constructor(
    private val remoteApi: CalendarRemoteDataSource,
) : UserCalendarRepository {

    override suspend fun getUserCalendarInfo(
        fromDate: LocalDate,
        toDate: LocalDate,
    ): TypedResult<UserCalendarInfo, CommonError> = executeCommonNetworkCall {
        coroutineScope {
            val events = async { remoteApi.getEvents(fromDate, toDate) }

            UserCalendarInfo(
                events = events.await(),
            )
        }
    }
}