package com.arakim.googlecalendarclone.domain.calendarsetup.usecases

import com.arakim.googlecalendarclone.domain.calendarsetup.CalendarSetUpRepository
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import javax.inject.Inject

class GetCalendarSetUpUseCase @Inject constructor(
    private val calendarSetUpRepository: CalendarSetUpRepository
) {
    operator fun invoke(): TypedResult<CalendarSetUp, CommonError> = calendarSetUpRepository.getSetUp()
}