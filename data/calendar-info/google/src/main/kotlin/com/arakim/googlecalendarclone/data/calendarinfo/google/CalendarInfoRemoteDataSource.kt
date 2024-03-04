package com.arakim.googlecalendarclone.data.calendarinfo.google

import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarInfo
import javax.inject.Inject

class CalendarInfoRemoteDataSource @Inject constructor() {

    suspend fun getCalendarInfo(): CalendarInfo {
        return CalendarInfo("Calendar Info")
    }
}