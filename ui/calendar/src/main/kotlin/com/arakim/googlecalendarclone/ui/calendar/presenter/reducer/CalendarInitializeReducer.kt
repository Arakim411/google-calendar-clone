package com.arakim.googlecalendarclone.ui.calendar.presenter.reducer

import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.domain.calendarsetup.usecases.GetCalendarSetUpFlow
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializeAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializedErrorAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializedSuccessAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.InitializingState
import com.arakim.googlecalendarclone.ui.calendar.presenter.helpers.GetCalendarStateHelper
import com.arakim.googlecalendarclone.ui.common.calendarrange.GetCalendarRangeHelper
import com.arakim.googlecalendarclone.util.kotlin.onFailure
import com.arakim.googlecalendarclone.util.kotlin.onSuccess
import com.arakim.googlecalendarclone.util.kotlin.yielded
import com.arakim.googlecalendarclone.util.mvi.StateReducer
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import javax.inject.Inject

class CalendarInitializeReducer @Inject constructor(
    private val getCalendarRangeHelper: GetCalendarRangeHelper,
    private val getCalendarSetUp: GetCalendarSetUpFlow,
    private val getCalendarStateHelper: GetCalendarStateHelper,
) : StateReducer<CalendarState, CalendarAction, InitializationAction>() {

    private var initializeJob: Job? = null

    override fun CalendarState.reduce(action: InitializationAction): CalendarState = when (action) {
        is InitializedSuccessAction -> reduceInitializedSuccessAction(action)
        is InitializedErrorAction -> reduceInitializeErrorAction(action)
        InitializeAction -> reduceInitializeAction()
    }

    private fun CalendarState.reduceInitializedSuccessAction(
        action: InitializedSuccessAction,
    ) = action.newState

    private fun CalendarState.reduceInitializeErrorAction(
        action: InitializedErrorAction,
    ): CalendarState = CalendarState.ErrorState(action.error)

    private fun reduceInitializeAction(): CalendarState {
        initialize()
        return InitializingState
    }

    private fun initialize() {
        initializeJob?.cancel()
        initializeJob = coroutineScope.yielded {
            getCalendarSetUp().collectLatest {
                it.onSuccess { setup ->
                    initialize(setup)
                }.onFailure { error ->
                    onAction(InitializedErrorAction(error))
                }
            }
        }
    }
    // TODO do not download again calendar range when set up is changed
    private suspend fun initialize(setUp: CalendarSetUp) {
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