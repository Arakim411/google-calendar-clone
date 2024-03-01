package com.arakim.googlecalendarclone.data.calendarsetup

import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp

val DefaultCalendarSetUp = CalendarSetUp(
    rangeType = CalendarRangeType.Schedule,
    isEventsChecked = true,
    isTasksChecked = true,
    isBirthdaysChecked = true,
    isHolidaysChecked = true
)