package com.arakim.googlecalendarclone.ui.common.calendarrange

import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent
import com.arakim.googlecalendarclone.domain.calendarinfo.useCases.GetUserCalendarInfoUseCase
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarDayUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarMonthUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarRangeUiModel
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.getOrThrow
import com.arakim.googlecalendarclone.util.kotlin.onFailure
import java.time.LocalDate
import javax.inject.Inject

internal typealias Event = CalendarEvent
internal typealias Day = CalendarDayUiModel
typealias Month = CalendarMonthUiModel

class GetCalendarRangeHelper @Inject constructor(
    private val getUseCalendar: GetUserCalendarInfoUseCase,
    private val buildCalendarRange: BuildCalendarRangeSubHelper,
) {

    suspend operator fun invoke(referenceDate: LocalDate): TypedResult<CalendarRangeUiModel, CommonError> {
        val userCalendar = getUseCalendar(
            fromDate = referenceDate.minusDays(CalendarRangeDays),
            toDate = referenceDate.plusDays(CalendarRangeDays)
        ).onFailure {
            return TypedResult.failure(it)
        }

        val calendarRange = buildCalendarRange(
            userCalendar.getOrThrow(),
            referenceDate.minusDays(CalendarRangeDays),
            referenceDate.plusDays(CalendarRangeDays),
        )
        return TypedResult.success(calendarRange)
    }

    companion object {
        private const val CalendarRangeDays = 360L
    }
}