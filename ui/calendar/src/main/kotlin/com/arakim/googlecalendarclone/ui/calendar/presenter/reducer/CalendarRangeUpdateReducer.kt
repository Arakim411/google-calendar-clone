package com.arakim.googlecalendarclone.ui.calendar.presenter.reducer

import androidx.compose.runtime.MutableState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.UpdateAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.UpdateAction.AdditionalRangeLoadedAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.UpdateAction.UserScrolledToMonthAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarSideEffect
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState
import com.arakim.googlecalendarclone.ui.calendar.presenter.helpers.LoadAdditionalRangeHelper
import com.arakim.googlecalendarclone.ui.calendar.presenter.helpers.MergeAdditionalRangeHelper
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.toCalendarDay
import com.arakim.googlecalendarclone.util.kotlin.yielded
import com.arakim.googlecalendarclone.util.mvi.StateReducerWithSideEffect
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class CalendarRangeUpdateReducer @Inject constructor(
    private val loadAdditionalRange: LoadAdditionalRangeHelper,
    private val mergeAdditionalRangeHelper: MergeAdditionalRangeHelper,
) : StateReducerWithSideEffect<CalendarState, CalendarAction, UpdateAction, CalendarSideEffect>() {

    private var loadRangeJob: Job? = null

    override fun CalendarState.reduce(action: UpdateAction): CalendarState = when (this) {
        is ReadyState -> reduceUpdateAction(action)
        else -> logInvalidState(this)
    }

    private fun ReadyState.reduceUpdateAction(action: UpdateAction): CalendarState = when (action) {
        is UserScrolledToMonthAction -> reduceUserScrolledToMonthAction(action)
        is AdditionalRangeLoadedAction -> with(mergeAdditionalRangeHelper) {
            getStateWithMergedRange(action.range)
        }
    }

    private fun ReadyState.reduceUserScrolledToMonthAction(action: UserScrolledToMonthAction): CalendarState {
        val day = action.month.toCalendarDay()
        (selectedDay as MutableState).value = day
        emitSideEffect(CalendarSideEffect.SelectedDayChanged(day))
        loadAdditionalRangeIfNeeded()
        return this
    }

    private fun ReadyState.loadAdditionalRangeIfNeeded() {
        if (loadRangeJob?.isActive == true) {
            return
        }
        loadRangeJob = coroutineScope.yielded {
            with(loadAdditionalRange) { loadAdditionalRangeIfNeeded() }?.let {
                onAction(AdditionalRangeLoadedAction(it))
            }
            delay(LoadAdditionalInterval)
        }
    }

    companion object {
        private val LoadAdditionalInterval = 50.milliseconds
    }
}