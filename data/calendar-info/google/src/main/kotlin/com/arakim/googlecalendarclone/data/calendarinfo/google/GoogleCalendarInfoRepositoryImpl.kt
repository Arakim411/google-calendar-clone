package com.arakim.googlecalendarclone.data.calendarinfo.google

import com.arakim.googlecalendarclone.domain.calendarinfo.CalendarInfoRepository
import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarInfo
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.executeCommonNetworkCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleCalendarInfoRepositoryImpl @Inject constructor(
    private val remoteApi: CalendarInfoRemoteDataSource,
) : CalendarInfoRepository {

    override suspend fun getCalendarInfo(): TypedResult<CalendarInfo, CommonError> =
        executeCommonNetworkCall {
            remoteApi.getCalendarInfo()
        }
}