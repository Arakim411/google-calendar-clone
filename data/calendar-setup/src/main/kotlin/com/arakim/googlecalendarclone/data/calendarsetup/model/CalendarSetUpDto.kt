package com.arakim.googlecalendarclone.data.calendarsetup.model

import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp

data class CalendarSetUpDto(
    val rangeType: String,
    val isEventsChecked: Boolean,
    val isTasksChecked: Boolean,
    val isBirthdaysChecked: Boolean,
    val isHolidaysChecked: Boolean
)

// TODO check with r8 and proguard
fun CalendarSetUpDto.toDomain(): CalendarSetUp = CalendarSetUp(
    rangeType = CalendarRangeType.valueOf(rangeType),
    isEventsChecked = isEventsChecked,
    isTasksChecked = isTasksChecked,
    isBirthdaysChecked = isBirthdaysChecked,
    isHolidaysChecked = isHolidaysChecked
)

fun CalendarSetUp.toDto(): CalendarSetUpDto = CalendarSetUpDto(
    rangeType = rangeType.name,
    isEventsChecked = isEventsChecked,
    isTasksChecked = isTasksChecked,
    isBirthdaysChecked = isBirthdaysChecked,
    isHolidaysChecked = isHolidaysChecked
)