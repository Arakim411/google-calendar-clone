package com.arakim.googlecalendarclone.data.calendarinfo.fake

import com.arakim.googlecalendarclone.domain.calendarinfo.UserCalendarRepository
import com.arakim.googlecalendarclone.domain.calendarinfo.model.UserCalendarInfo
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeCalendarInfoRepositoryImpl @Inject constructor() :
    com.arakim.googlecalendarclone.domain.calendarinfo.UserCalendarRepository {
    override suspend fun getUserCalendarInfo(
        fromDate: LocalDate,
        toDate: LocalDate,
    ): TypedResult<com.arakim.googlecalendarclone.domain.calendarinfo.model.UserCalendarInfo, CommonError> {
        return TypedResult.success(
            com.arakim.googlecalendarclone.domain.calendarinfo.model.UserCalendarInfo(
                userTasks = emptyList(),
                userEvents = emptyList(),
                worldEvents = emptyList(),
            )
        )
    }
}