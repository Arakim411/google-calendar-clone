package com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.schedule.monthscolumn

import androidx.compose.runtime.Immutable
import com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule.ScheduleMonthUiModel

@Immutable
sealed interface ScheduleListItem {

    @Immutable
    data class UserItem(val item: ScheduleMonthUiModel) : ScheduleListItem

    @Immutable
    data object OutOfBoundsItem : ScheduleListItem
}