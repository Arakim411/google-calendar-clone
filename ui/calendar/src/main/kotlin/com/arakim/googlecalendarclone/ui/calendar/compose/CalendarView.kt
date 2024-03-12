package com.arakim.googlecalendarclone.ui.calendar.compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.ScheduleStateView
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarPresenter
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ErrorState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.IdleState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.InitializingState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.DayState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.MonthState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.ScheduleState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.ThreeDaysState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.WeekState
import com.arakim.googlecalendarclone.ui.common.CommonErrorView
import com.arakim.googlecalendarclone.ui.common.CommonLoaderView

@Composable
fun CalendarView(presenter: CalendarPresenter) {

    val state = presenter.stateFlow.collectAsStateWithLifecycle()

    Crossfade(targetState = state.value, label = "") { stateValue ->
        when (stateValue) {
            // TODO add retry
            is ErrorState -> CommonErrorView(stateValue.error)
            IdleState -> Unit
            InitializingState -> CommonLoaderView()
            is ReadyState -> ReadyState(stateValue)
        }
    }
}

@Composable
private fun ReadyState(state: ReadyState) {
    when (state) {
        is DayState -> NotReadyView(text = "dayState")
        is ThreeDaysState -> NotReadyView(text = "3dayState")
        is MonthState -> NotReadyView(text = "month")
        is ScheduleState -> ScheduleStateView(state)
        is WeekState -> NotReadyView(text = "week")
    }
}

@Composable
private fun NotReadyView(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "state not ready: $text")
    }
}