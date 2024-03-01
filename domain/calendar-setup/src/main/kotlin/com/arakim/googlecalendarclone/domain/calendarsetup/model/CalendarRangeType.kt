package com.arakim.googlecalendarclone.domain.calendarsetup.model

enum class CalendarRangeType(
    val id: String,
) {
    Schedule("schedule"),
    Day("day"),
    ThreeDays("3days"),
    Week("week"),
    Month("month")
}
