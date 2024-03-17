package com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.schedule.monthscolumn

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.schedule.monthscolumn.ScheduleListItem.OutOfBoundsItem
import com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.schedule.monthscolumn.ScheduleListItem.UserItem
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.ScheduleState
import com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule.toCalendarMonthUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarMonthUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Immutable
internal class ScheduleLazyListView(
    internal val lazyListState: LazyListState,
) : ScheduleLazyListState {

    private val _firstVisibleMonth = mutableStateOf<CalendarMonthUiModel?>(null)
    private lateinit var state: ScheduleState
    private val initialMonthIndex = Int.MAX_VALUE / 2

    @Stable
    override val firstVisibleMonth: State<CalendarMonthUiModel?> = _firstVisibleMonth

    fun initialize(scope: CoroutineScope) {
        scope.launch {
            snapshotFlow { lazyListState.firstVisibleItemIndex }
                .collectLatest { index ->
                    _firstVisibleMonth.value = (getItem(index) as? UserItem)?.item?.toCalendarMonthUiModel()
                }
        }
    }

    @Stable
    fun onStateUpdated(state: ScheduleState) {
        this.state = state
    }

    suspend fun scrollToInitialIndex() {
        lazyListState.scrollToItem(initialMonthIndex)
    }

    fun getItem(viewIndex: Int): ScheduleListItem = when {
        viewIndex == initialMonthIndex -> UserItem(state.initialMonth)
        viewIndex > initialMonthIndex -> getMonthsAfter(viewIndex)
        else -> getMonthsBefore(viewIndex)
    }

    private fun getMonthsAfter(viewIndex: Int): ScheduleListItem {
        val index = (viewIndex - initialMonthIndex - 1)
        if (index > state.monthsAfterInitial.lastIndex) return OutOfBoundsItem
        return UserItem(state.monthsAfterInitial[index])
    }

    private fun getMonthsBefore(viewIndex: Int): ScheduleListItem {
        val index = (initialMonthIndex - viewIndex - 1)
        if (index > state.monthsBeforeInitial.lastIndex) return OutOfBoundsItem
        return UserItem(state.monthsBeforeInitial[index])
    }
}

@Composable
fun rememberInfinityLazyListState(): ScheduleLazyListState {

    val lazyListState = rememberLazyListState()
    return remember { ScheduleLazyListView(lazyListState) }
}