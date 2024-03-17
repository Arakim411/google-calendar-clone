package com.arakim.googlecalendarclone.ui.calendar.presenter

import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarMonthUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarRangeUiModel
import com.arakim.googlecalendarclone.util.kotlin.CommonError

sealed interface CalendarAction {

    sealed interface InitializationAction : CalendarAction {
        data object InitializeAction : InitializationAction
        data class InitializedErrorAction(val error: CommonError) : InitializationAction
        data class CalendarSetUpChangedAction(val newSetUp: CalendarSetUp) : InitializationAction
        data class InitializedSuccessAction(val newState: ReadyState) : InitializationAction
    }

    sealed interface UpdateAction : CalendarAction {
        data class UserScrolledToMonthAction(val month: CalendarMonthUiModel) : UpdateAction
        data class AdditionalRangeLoadedAction(val range: CalendarRangeUiModel) : UpdateAction
        data object ScrollToTodayAction : UpdateAction
    }
}