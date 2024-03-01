package com.arakim.googlecalendarclone.domain.calendarsetup

import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult

interface CalendarSetUpRepository {

    fun getSetUp(): TypedResult<CalendarSetUp, CommonError>
    fun saveSetUp(calendarSetUp: CalendarSetUp)
}