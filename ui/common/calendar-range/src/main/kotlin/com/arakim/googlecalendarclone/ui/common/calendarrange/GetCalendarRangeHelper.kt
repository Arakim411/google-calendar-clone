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
        val fromDate = referenceDate.minusMonths(CalendarRangeMonths)
        val toDate = referenceDate.plusMonths(CalendarRangeMonths)
        return invoke(fromDate, toDate)
    }

    suspend operator fun invoke(
        fromDate: LocalDate,
        toDate: LocalDate,
    ): TypedResult<CalendarRangeUiModel, CommonError> {

        val userCalendar = getUseCalendar(
            fromDate = fromDate,
            toDate = toDate
        ).onFailure {
            return TypedResult.failure(it)
        }

        val calendarRange = buildCalendarRange(
            userCalendar.getOrThrow(),
            fromDate,
            toDate,
        )
        return TypedResult.success(calendarRange)
    }

    companion object {
        private const val CalendarRangeMonths = 36L
    }
}
