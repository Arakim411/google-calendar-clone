package com.arakim.googlecalendarclone.data.calendarinfo.fake

import com.arakim.googlecalendarclone.domain.calendarinfo.CalendarInfoRepository
import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarInfo
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeCalendarInfoRepositoryImpl @Inject constructor() : CalendarInfoRepository {
    override suspend fun getCalendarInfo(): TypedResult<CalendarInfo, CommonError> {
        return TypedResult.Success(CalendarInfo("fake calendar"))
    }
}