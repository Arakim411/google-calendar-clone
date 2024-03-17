package com.arakim.googlecalendarclone.ui.calendar.presenter.helpers

import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState
import com.arakim.googlecalendarclone.ui.common.calendarrange.GetCalendarRangeHelper
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarRangeUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.toLocalDate
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.getOrNull
import javax.inject.Inject

class LoadAdditionalRangeHelper @Inject constructor(
    private val getCalendarRangeUiModel: GetCalendarRangeHelper,
) {

    suspend fun ReadyState.loadAdditionalRangeIfNeeded(): CalendarRangeUiModel? = when {
        shouldLoadFutureRange() -> loadFutureRange().getOrNull()
        shouldLoadPastRange() -> loadPastRange().getOrNull()
        else -> null
    }

    private fun ReadyState.shouldLoadPastRange(): Boolean =
        selectedDay.value.toLocalDate().minusMonths(MonthThreshold).isBefore(calendarInfo.fromDate)

    private fun ReadyState.shouldLoadFutureRange(): Boolean =
        selectedDay.value.toLocalDate().plusMonths(MonthThreshold).isAfter(calendarInfo.toDate)

    private suspend fun ReadyState.loadFutureRange(): TypedResult<CalendarRangeUiModel, CommonError> {
        val fromDate = calendarInfo.toDate.plusDays(1)
        val toDate = fromDate.plusMonths(AdditionalRangeMonths)

        return getCalendarRangeUiModel(fromDate, toDate)
    }

    private suspend fun ReadyState.loadPastRange(): TypedResult<CalendarRangeUiModel, CommonError> {
        val toDate = calendarInfo.fromDate.minusDays(1)
        val fromDate = toDate.minusMonths(AdditionalRangeMonths)

        return getCalendarRangeUiModel(fromDate, toDate)
    }

    companion object {
        private const val MonthThreshold = 5L
        private const val AdditionalRangeMonths = 36L
    }
}