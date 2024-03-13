package com.arakim.googlecalendarclone.ui.calendar.presenter.reducer

import androidx.compose.runtime.MutableState
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType.Day
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType.Month
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType.Schedule
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType.ThreeDays
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType.Week
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.domain.calendarsetup.usecases.GetCalendarSetUpFlow
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.CalendarSetUpChangedAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializeAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializedErrorAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializedSuccessAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.InitializingState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.DayState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.MonthState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.ScheduleState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.ThreeDaysState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.WeekState
import com.arakim.googlecalendarclone.ui.calendar.presenter.helpers.GetCalendarStateHelper
import com.arakim.googlecalendarclone.ui.common.calendarrange.GetCalendarRangeHelper
import com.arakim.googlecalendarclone.util.compose.ImmutableWrapper
import com.arakim.googlecalendarclone.util.kotlin.onFailure
import com.arakim.googlecalendarclone.util.kotlin.onSuccess
import com.arakim.googlecalendarclone.util.kotlin.yielded
import com.arakim.googlecalendarclone.util.mvi.StateReducer
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest

class CalendarInitializeReducer @Inject constructor(
    private val getCalendarRangeHelper: GetCalendarRangeHelper,
    private val getCalendarSetUp: GetCalendarSetUpFlow,
    private val getCalendarStateHelper: GetCalendarStateHelper,
) : StateReducer<CalendarState, CalendarAction, InitializationAction>() {

    private var listenForSetUpChangeJob: Job? = null
    private var initializeJob: Job? = null

    override fun CalendarState.reduce(action: InitializationAction): CalendarState = when (action) {
        is InitializedSuccessAction -> reduceInitializedSuccessAction(action)
        is InitializedErrorAction -> reduceInitializeErrorAction(action)
        InitializeAction -> reduceInitializeAction()
        is CalendarSetUpChangedAction -> reduceCalendarSetUpChangeAction(action)
    }

    private fun reduceInitializedSuccessAction(
        action: InitializedSuccessAction,
    ) = action.newState

    private fun reduceInitializeErrorAction(
        action: InitializedErrorAction,
    ): CalendarState = CalendarState.ErrorState(action.error)

    private fun reduceInitializeAction(): CalendarState {
        listenForCalendarSetUpChange()
        return InitializingState
    }

    // TODO do not fetch setUp again when rangeType is changed
    private fun CalendarState.reduceCalendarSetUpChangeAction(
        action: CalendarSetUpChangedAction,
    ): CalendarState = when (this) {
        is ReadyState -> {
            onSetUpChanged(action.newSetUp)
            this
        }

        else -> {
            initialize(action.newSetUp)
            InitializingState
        }
    }

    private fun ReadyState.onSetUpChanged(setUp: CalendarSetUp) {
        if (this.toRangeType() == setUp.rangeType) {
            (this.calendarSetUp as MutableState).value = ImmutableWrapper(setUp)
        } else {
            coroutineScope.yielded {
                initialize(setUp)
            }
        }
    }

    private fun listenForCalendarSetUpChange() {
        listenForSetUpChangeJob?.cancel()
        listenForSetUpChangeJob = coroutineScope.yielded {
            getCalendarSetUp().collectLatest {
                it.onSuccess { setup ->
                    onAction(CalendarSetUpChangedAction(setup))
                }.onFailure { error ->
                    onAction(InitializedErrorAction(error))
                }
            }
        }
    }

    private fun initialize(setUp: CalendarSetUp) {
        initializeJob?.cancel()
        initializeJob = coroutineScope.yielded {
            getCalendarRangeHelper(LocalDate.now())
                .onSuccess {
                    val state = getCalendarStateHelper(it, setUp)
                    onAction(InitializedSuccessAction(state))
                }
                .onFailure {
                    onAction(InitializedErrorAction(it))
                }
        }
    }
}

private fun ReadyState.toRangeType(): CalendarRangeType = when (this) {
    is DayState -> Day
    is ThreeDaysState -> ThreeDays
    is MonthState -> Month
    is ScheduleState -> Schedule
    is WeekState -> Week
}