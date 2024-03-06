package com.arakim.googlecalendarclone.domain.calendar.usercalendar

import com.arakim.googlecalendarclone.domain.calendar.usercalendar.model.UserCalendarInfo
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import java.time.LocalDate

interface UserCalendarRepository {

    suspend fun getUserCalendarInfo(
        fromDate: LocalDate,
        toDate: LocalDate,
    ): TypedResult<UserCalendarInfo, CommonError>
}