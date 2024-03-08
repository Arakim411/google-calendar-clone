package com.arakim.googlecalendarclone.ui.calendar.presenter.reducer

import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializeAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializedErrorAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializedSuccessAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.InitializingState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.ScheduleState
import com.arakim.googlecalendarclone.ui.common.calendarrange.GetCalendarRangeHelper
import com.arakim.googlecalendarclone.util.kotlin.onFailure
import com.arakim.googlecalendarclone.util.kotlin.onSuccess
import com.arakim.googlecalendarclone.util.kotlin.yielded
import com.arakim.googlecalendarclone.util.mvi.StateReducer
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.Job

class CalendarInitializeReducer @Inject constructor(
    private val getCalendarRangeHelper: GetCalendarRangeHelper,
) : StateReducer<CalendarState, CalendarAction, InitializationAction>() {

    private var initializeJob: Job? = null

    override fun CalendarState.reduce(action: InitializationAction): CalendarState = when (action) {
        is InitializedSuccessAction -> reduceInitializedSuccessAction(action)
        is InitializedErrorAction -> reduceInitializeErrorAction(action)
        InitializeAction -> reduceInitializeAction()
    }

    private fun CalendarState.reduceInitializedSuccessAction(action: InitializedSuccessAction) = when (this) {
        InitializingState -> action.newState
        else -> logInvalidState()
    }

    private fun CalendarState.reduceInitializeErrorAction(
        action: InitializedErrorAction,
    ): CalendarState = when (this) {
        InitializingState -> CalendarState.ErrorState(action.error)
        else -> logInvalidState()
    }

    private fun reduceInitializeAction(): CalendarState {
        initialize()
        return InitializingState
    }

    private fun initialize() {
        initializeJob?.cancel()
        initializeJob = coroutineScope.yielded {
            getCalendarRangeHelper(LocalDate.now())
                .onSuccess {
                    onAction(InitializedSuccessAction(ScheduleState(it)))
                }
                .onFailure {
                    onAction(InitializedErrorAction(it))
                }
        }
    }
}