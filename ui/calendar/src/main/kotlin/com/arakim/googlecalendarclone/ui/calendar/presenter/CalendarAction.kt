package com.arakim.googlecalendarclone.ui.calendar.presenter

import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState
import com.arakim.googlecalendarclone.util.kotlin.CommonError

sealed interface CalendarAction {

    sealed interface InitializationAction : CalendarAction {
        data object InitializeAction : InitializationAction
        data class InitializedErrorAction(val error: CommonError) : InitializationAction
        data class InitializedSuccessAction(val newState: ReadyState) : InitializationAction
    }
}