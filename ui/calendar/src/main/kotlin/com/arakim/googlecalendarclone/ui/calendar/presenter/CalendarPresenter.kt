package com.arakim.googlecalendarclone.ui.calendar.presenter

import androidx.compose.runtime.Immutable
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.IdleState
import com.arakim.googlecalendarclone.ui.calendar.presenter.reducer.CalendarInitializeReducer
import com.arakim.googlecalendarclone.util.mvi.ReducerPresenterWithSideEffect
import javax.inject.Inject

@Immutable
class CalendarPresenter @Inject constructor(
    initializeReducer: CalendarInitializeReducer,
) : ReducerPresenterWithSideEffect<CalendarState, CalendarAction, CalendarSideEffect>(IdleState) {

    init {
        registerReducer<InitializationAction>(initializeReducer)
    }
}