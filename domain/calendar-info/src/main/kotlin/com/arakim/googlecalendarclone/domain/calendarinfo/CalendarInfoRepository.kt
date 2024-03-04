package com.arakim.googlecalendarclone.domain.calendarinfo

import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarInfo
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult

interface CalendarInfoRepository {

    suspend fun getCalendarInfo(): TypedResult<CalendarInfo, CommonError>
}