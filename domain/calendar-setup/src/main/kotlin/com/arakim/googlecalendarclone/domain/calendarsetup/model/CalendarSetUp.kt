package com.arakim.googlecalendarclone.domain.calendarsetup.model

data class CalendarSetUp(
    val rangeType: CalendarRangeType,
    val isEventsChecked: Boolean,
    val isTasksChecked: Boolean,
    val isBirthdaysChecked: Boolean,
    val isHolidaysChecked: Boolean,
)