@file:Suppress("MagicNumber")

package com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arakim.googlecalendarclone.domain.calendarinfo.model.CalendarEvent
import com.arakim.googlecalendarclone.domain.calendarsetup.model.CalendarSetUp
import com.arakim.googlecalendarclone.ui.calendar.compose.asShortDay
import com.arakim.googlecalendarclone.ui.calendar.compose.getBackgroundColor
import com.arakim.googlecalendarclone.ui.calendar.compose.getHoursRange
import com.arakim.googlecalendarclone.ui.calendar.compose.getTextColor
import com.arakim.googlecalendarclone.ui.calendar.compose.shouldShowEvent
import com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.ScheduleConsts as Consts
import com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.getColorsForMonth
import com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.schedule.monthscolumn.InfinityLazyList
import com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.schedule.monthscolumn.rememberInfinityLazyListState
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.UpdateAction.UserScrolledToMonthAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarState.ReadyState.ScheduleState
import com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule.ScheduleDayRangeUiModel
import com.arakim.googlecalendarclone.ui.calendar.presenter.model.schedule.ScheduleMonthUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarDayUiModel
import com.arakim.googlecalendarclone.util.compose.ImmutableWrapper
import com.arakim.googlecalendarclone.util.compose.asMonthString
import kotlinx.coroutines.flow.collectLatest

// TODO check performance
// TODO setUp with staticCompositionLocalOf
// TODO add animations

@Composable
fun ScheduleStateView(
    state: ScheduleState,
    onAction: (CalendarAction) -> Unit,
) {

    val lazyListState = rememberInfinityLazyListState()

    LaunchedEffect(Unit) {
        snapshotFlow { lazyListState.firstVisibleMonth.value }
            .collectLatest { month ->
                month ?: return@collectLatest
                onAction(UserScrolledToMonthAction(month))
            }
    }

    InfinityLazyList(
        listState = lazyListState,
        scheduleState = state,
    ) { month ->

        MonthView(month, state.calendarSetUp.value)
        Spacer(modifier = Modifier.height(Consts.ItemSpacing))
    }
}

@Composable
private fun MonthView(
    month: ScheduleMonthUiModel,
    setUp: ImmutableWrapper<CalendarSetUp>,
) {
    MonthTitle(month = month)
    Spacer(modifier = Modifier.height(Consts.ItemSpacing))
    month.daysRange.forEachIndexed { index, dayRange ->
        DayRangeView(month, dayRange, setUp)

        if (index != month.daysRange.lastIndex) {
            Spacer(modifier = Modifier.height(Consts.ItemSpacing))
        }
    }
}

// TODO images as background
@Composable
private fun MonthTitle(month: ScheduleMonthUiModel) {
    val gradientColors = remember { month.monthValue.getColorsForMonth() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Consts.MonthTitle.Height)
            .background(Brush.verticalGradient(colors = gradientColors))
            .padding(top = Consts.MonthTitle.PaddingTop, start = Consts.MonthTitle.PaddingStart)
    ) {
        Text(
            text = "${month.monthValue.asMonthString()} ${month.year}",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun DayRangeView(
    month: ScheduleMonthUiModel,
    dayRange: ScheduleDayRangeUiModel,
    setUp: ImmutableWrapper<CalendarSetUp>,
) {
    DayRangeContainer(
        centerView = {
            Text(
                text = "${month.monthValue.asMonthString()} ${dayRange.startDay} - ${dayRange.endDay}",
                style = MaterialTheme.typography.bodySmall,
            )
        },
    )

    dayRange.events.value.forEach {

        val filteredEvents = remember(it, setUp.value) {
            it.value.filter { event -> event.shouldShowEvent(setUp.value) }
        }
        if (filteredEvents.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Consts.ItemSpacing))
            DayView(day = it.key, events = filteredEvents)
        }
    }
}

// TODO use immutable list
@Composable
private fun DayView(
    day: CalendarDayUiModel,
    events: List<CalendarEvent>,
) {
    DayRangeContainer(
        leadingView = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = day.dayOfWeek.asShortDay(), style = MaterialTheme.typography.labelMedium)
                Text(
                    text = "${day.dayOfMonth}",
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp)
                )
            }
        },
        centerView = {
            Column {
                events.forEachIndexed { index, event ->
                    EventView(
                        topLine = event.title,
                        bottomLine = event.getHoursRange(),
                        backgroundColor = event.getBackgroundColor(),
                        textColor = event.getTextColor(),
                    )
                    if (index != events.lastIndex) {
                        Spacer(modifier = Modifier.height(Consts.ItemSpacing))
                    }
                }
            }
        },
    )
}

@Composable
private fun EventView(
    modifier: Modifier = Modifier,
    topLine: String,
    bottomLine: String?,
    backgroundColor: Color,
    textColor: Color,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = MaterialTheme.shapes.small)
            .padding(8.dp)
    ) {
        Column {
            Text(text = topLine, style = MaterialTheme.typography.bodyMedium, color = textColor)
            if (bottomLine != null) {
                Text(text = bottomLine, style = MaterialTheme.typography.bodyMedium, color = textColor)
            }
        }
    }
}

@Composable
internal fun DayRangeContainer(
    leadingView: @Composable () -> Unit = {},
    centerView: @Composable () -> Unit,
) {

    Row(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.Center) { leadingView() }
        Box(
            modifier = Modifier.weight(10f),
        ) {
            centerView()
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}