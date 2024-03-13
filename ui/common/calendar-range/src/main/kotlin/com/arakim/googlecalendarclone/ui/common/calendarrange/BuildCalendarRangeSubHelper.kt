package com.arakim.googlecalendarclone.ui.common.calendarrange

import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent
import com.arakim.googlecalendarclone.domain.calendarinfo.model.UserCalendarInfo
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarInfoUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarRangeUiModel
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class BuildCalendarRangeSubHelper @Inject constructor() {

    operator fun invoke(
        userCalendarInfo: UserCalendarInfo,
        fromDate: LocalDate,
        toDate: LocalDate,
    ): CalendarRangeUiModel {
        val monthsToEvents = hashMapOf<Month, HashMap<Day, List<Event>>>()

        forEachDayInRange(fromDate, toDate) { month, day ->
            if (monthsToEvents[month] == null) {
                monthsToEvents[month] = hashMapOf()
            }
            monthsToEvents[month]!![day] = userCalendarInfo.getEventsInDay(day)
        }

        return CalendarRangeUiModel(
            fromDate = fromDate,
            toDate = toDate,
            eventsInfo = CalendarInfoUiModel(monthsToEvents)
        )
    }

    private inline fun forEachDayInRange(
        fromDate: LocalDate,
        toDate: LocalDate,
        action: (Month, Day) -> Unit,
    ) {
        var currentDate = fromDate
        while (currentDate < toDate) {
            action(currentDate.toCalendarMonth(), currentDate.toCalendarDay())
            currentDate = currentDate.plusDays(1)
        }
    }

    private fun List<CalendarEvent>.getInDay(day: Day) = filter { it.startDateTime.toCalendarDay() == day }

    private fun UserCalendarInfo.getEventsInDay(day: Day): List<Event> =
        userEvents.getInDay(day) + userTasks.getInDay(day) + worldEvents.getInDay(day)
}

fun LocalDate.toCalendarDay(): Day = Day(
    dayOfWeek = dayOfWeek.value,
    dayOfMonth = dayOfMonth,
    year = year,
    month = monthValue,
)

fun LocalDate.toCalendarMonth(): Month = Month(
    year = year,
    monthValue = monthValue
)

fun LocalDateTime.toCalendarDay(): Day = Day(
    dayOfWeek = dayOfWeek.value,
    dayOfMonth = dayOfMonth,
    year = year,
    month = monthValue,
)