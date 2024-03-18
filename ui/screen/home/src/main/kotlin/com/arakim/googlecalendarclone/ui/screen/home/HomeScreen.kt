package com.arakim.googlecalendarclone.ui.screen.home

import android.widget.CalendarView
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.arakim.googlecalendarclone.ui.calendar.compose.CalendarView
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializeAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.UpdateAction.ScrollToTodayAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarSideEffect.ScrollToDaySideEffect
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarSideEffect.SelectedDayChanged
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarDayUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.isBefore
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.toLocalDate
import com.arakim.googlecalendarclone.util.compose.asMonthString
import java.time.LocalDate
import java.time.ZoneOffset
import kotlinx.coroutines.flow.collectLatest

// TODO handle window size class
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    changeIsDrawerOpen: (Boolean) -> Unit,
) {
    val selectedDay = remember { mutableStateOf<CalendarDayUiModel?>(null) }
    val isCalendarOpen = remember { mutableStateOf(false) }

    val calendarSize = animateDpAsState(targetValue = if (isCalendarOpen.value) 300.dp else 0.dp, label = "")

    val context = LocalContext.current

    fun onSelectedDayChanged(day: CalendarDayUiModel) {
        selectedDay.value = day
        isCalendarOpen.value = false
    }

    LaunchedEffect(Unit) {
        viewModel.calendarPresenter.onAction(InitializeAction)
    }

    LaunchedEffect(Unit) {
        viewModel.calendarPresenter.sideEffectFlow.collectLatest {
            when (it) {
                is SelectedDayChanged -> onSelectedDayChanged(it.day)
                is ScrollToDaySideEffect -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                onNavigateIconClick = { changeIsDrawerOpen(true) },
                selectedDay = selectedDay.value,
                isCalendarOpen = isCalendarOpen.value,
                onMonthClick = { day ->
                    isCalendarOpen.value = !isCalendarOpen.value
                },
                onTodayClick = {
                    viewModel.calendarPresenter.onAction(ScrollToTodayAction)
                }
            )
        },
    ) { padding ->

        Column(modifier = Modifier.padding(padding)) {

            if (calendarSize.value > 0.dp) {
                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(calendarSize.value),
                    factory = {
                        CalendarView(context).apply {
                            selectedDay.value?.also { day ->
                                date =
                                    day.toLocalDate().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
                            }
                            setOnDateChangeListener { _, _, _, _ ->
                                Toast.makeText(context, "not ready yet", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            CalendarView(presenter = viewModel.calendarPresenter)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onNavigateIconClick: () -> Unit,
    selectedDay: CalendarDayUiModel?,
    isCalendarOpen: Boolean,
    onMonthClick: (day: CalendarDayUiModel) -> Unit,
    onTodayClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (selectedDay != null) {
                    SelectedMonthView(selectedDay, isCalendarOpen, onMonthClick)
                    TopBarRightItems(onTodayClick)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        navigationIcon = {
            if (selectedDay != null) {
                Icon(
                    modifier = Modifier.clickable(onClick = onNavigateIconClick),
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = null,
                )
            }
        },
    )
}

@Composable
private fun RowScope.TopBarRightItems(onTodayClick: () -> Unit) {
    Box(
        modifier = Modifier.clickable { onTodayClick() },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = R.drawable.outline_calendar_today_24),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = remember { LocalDate.now().dayOfMonth.toString() },
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun SelectedMonthView(
    day: CalendarDayUiModel,
    isCalendarOpen: Boolean,
    onMonthClick: (day: CalendarDayUiModel) -> Unit,
) {

    val context = LocalContext.current
    val rotation = animateFloatAsState(targetValue = if (isCalendarOpen) -180f else 0f, label = "")
    AnimatedContent(
        targetState = day, transitionSpec = {
            val slideIntoDirection = if (initialState.isBefore(targetState)) Down else Up

            val enterAnim = slideIntoContainer(
                towards = slideIntoDirection,
            ).plus(fadeIn())

            val exitAnim = slideOutOfContainer(
                towards = slideIntoDirection,
            ).plus(fadeOut())

            enterAnim.togetherWith(exitAnim)
        }, label = ""
    ) {
        Row(modifier = Modifier.clickable { onMonthClick(day) }) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = it.month.asMonthString(context),
            )
            Image(
                modifier = Modifier.rotate(rotation.value),
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = null
            )
        }
    }
}