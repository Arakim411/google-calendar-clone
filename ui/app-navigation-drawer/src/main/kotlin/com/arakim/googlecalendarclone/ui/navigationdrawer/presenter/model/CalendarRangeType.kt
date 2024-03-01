package com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.model

enum class CalendarRangeType(
    val id: String,
) {
    Schedule("schedule"),
    Day("day"),
    ThreeDays("3days"),
    Week("week"),
    Month("month")
}
