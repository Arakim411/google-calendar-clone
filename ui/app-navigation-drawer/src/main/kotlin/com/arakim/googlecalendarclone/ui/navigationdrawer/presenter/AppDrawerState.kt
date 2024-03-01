package com.arakim.googlecalendarclone.ui.navigationdrawer.presenter

import androidx.compose.runtime.Immutable
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarRangeType

@Immutable
sealed interface AppDrawerState {
    @Immutable
    data object IdleState : AppDrawerState

    @Immutable
    data object InitializingState : AppDrawerState

    data object ErrorState : AppDrawerState

    @Immutable
    data class ReadyState(
        val calendarRangeType: CalendarRangeType,
        val isEventsChecked: Boolean,
        val isTasksChecked: Boolean,
        val isBirthdaysChecked: Boolean,
        val isHolidaysChecked: Boolean
    ) : AppDrawerState
}