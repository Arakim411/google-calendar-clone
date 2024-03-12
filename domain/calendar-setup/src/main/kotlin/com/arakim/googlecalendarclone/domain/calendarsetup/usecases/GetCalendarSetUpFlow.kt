package com.arakim.googlecalendarclone.domain.calendarsetup.usecases

import com.arakim.googlecalendarclone.domain.calendarsetup.CalendarSetUpRepository
import javax.inject.Inject

class GetCalendarSetUpFlow @Inject constructor(
    private val calendarSetUpRepository: CalendarSetUpRepository,
) {

    operator fun invoke() = calendarSetUpRepository.getSetUpFlow()
}