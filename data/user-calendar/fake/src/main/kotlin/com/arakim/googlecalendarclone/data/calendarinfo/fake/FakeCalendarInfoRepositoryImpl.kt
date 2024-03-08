package com.arakim.googlecalendarclone.data.calendarinfo.fake

import com.arakim.googlecalendarclone.domain.calendar.usercalendar.UserCalendarRepository
import com.arakim.googlecalendarclone.domain.calendar.usercalendar.model.UserCalendarInfo
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeCalendarInfoRepositoryImpl @Inject constructor() : UserCalendarRepository {
    override suspend fun getUserCalendarInfo(
        fromDate: LocalDate,
        toDate: LocalDate,
    ): TypedResult<UserCalendarInfo, CommonError> {
        return TypedResult.success(
            UserCalendarInfo(
                userTasks = emptyList(),
                userEvents = emptyList(),
                worldEvents = emptyList(),
            )
        )
    }
}