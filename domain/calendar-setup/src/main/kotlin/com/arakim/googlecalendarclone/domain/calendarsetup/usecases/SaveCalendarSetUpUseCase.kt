package com.arakim.googlecalendarclone.domain.calendarsetup.usecases

import com.arakim.googlecalendarclone.domain.calendarsetup.CalendarSetUpRepository
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import javax.inject.Inject

class SaveCalendarSetUpUseCase @Inject constructor(
    private val calendarSetUpRepository: CalendarSetUpRepository
) {
    operator fun invoke(setUp: CalendarSetUp) {
        calendarSetUpRepository.saveSetUp(setUp)
    }
}