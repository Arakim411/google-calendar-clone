package com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.schedule.monthscolumn

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.ScheduleState
import com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule.ScheduleMonthUiModel

@Composable
fun InfinityLazyList(
    modifier: Modifier = Modifier,
    scheduleState: ScheduleState,
    listState: ScheduleLazyListState = rememberScheduleLazyListState(),
    itemContent: @Composable (item: ScheduleMonthUiModel) -> Unit,
) {
    val scope = rememberCoroutineScope()
    listState as ScheduleLazyListView

    LaunchedEffect(listState) {
        listState.initialize(scope)
    }

    remember(scheduleState) {
        listState.onStateUpdated(scheduleState)
        false
    }

    LaunchedEffect(Unit) {
        listState.scrollToInitialIndex()
    }

    LazyColumn(
        modifier = modifier,
        state = listState.lazyListState,
    ) {
        items(Int.MAX_VALUE) { index ->
            when (val item = listState.getItem(index)) {
                is ScheduleListItem.UserItem -> itemContent(item.item)
                is ScheduleListItem.OutOfBoundsItem -> ScheduleListSkeletonLoading()
            }
        }
    }
}