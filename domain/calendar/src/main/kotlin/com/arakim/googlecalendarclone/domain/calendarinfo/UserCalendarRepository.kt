package com.arakim.googlecalendarclone.domain.calendarinfo

import com.arakim.googlecalendarclone.domain.calendarinfo.model.UserCalendarInfo
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import java.time.LocalDate

interface UserCalendarRepository {

    suspend fun getUserCalendarInfo(
        fromDate: LocalDate,
        toDate: LocalDate,
    ): TypedResult<UserCalendarInfo, CommonError>
}