package com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule

import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarDayUiModel
import com.arakim.googlecalendarclone.util.compose.ImmutableWrapper

data class ScheduleDayRangeUiModel(
    val startDay: Int,
    val endDay: Int,
    val events: ImmutableWrapper<Map<CalendarDayUiModel, List<CalendarEvent>>>,
    val showYear: Boolean,
)