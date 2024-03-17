package com.arakim.googlecalendarclone.ui.screen.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arakim.googlecalendarclone.ui.calendar.compose.CalendarView
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializeAction
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarSideEffect.SelectedDayChanged
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.CalendarDayUiModel
import com.arakim.googlecalendarclone.ui.common.calendarrange.model.isBefore
import com.arakim.googlecalendarclone.util.compose.asMonthString
import kotlinx.coroutines.flow.collectLatest

// TODO handle window size class
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    changeIsDrawerOpen: (Boolean) -> Unit,
) {
    val selectedDay = remember { mutableStateOf<CalendarDayUiModel?>(null) }

    fun onSelectedDayChanged(day: CalendarDayUiModel) {
        selectedDay.value = day
    }

    LaunchedEffect(Unit) {
        viewModel.calendarPresenter.onAction(InitializeAction)
    }

    LaunchedEffect(Unit) {
        viewModel.calendarPresenter
            .sideEffectFlow
            .collectLatest {
                when (it) {
                    is SelectedDayChanged -> onSelectedDayChanged(it.day)
                }
            }
    }

    Scaffold(
        topBar = {
            TopBar(
                onNavigateIconClick = { changeIsDrawerOpen(true) },
                selectedDay = selectedDay.value,
            )
        },
    ) { padding ->

        Box(modifier = Modifier.padding(padding)) {
            CalendarView(presenter = viewModel.calendarPresenter)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onNavigateIconClick: () -> Unit,
    selectedDay: CalendarDayUiModel?,
) {
    TopAppBar(
        title = {
            if (selectedDay != null) {
                SelectedMonthView(day = selectedDay)
            }
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable(onClick = onNavigateIconClick),
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
            )
        },
    )
}

@Composable
private fun SelectedMonthView(day: CalendarDayUiModel) {
    val context = LocalContext.current
    AnimatedContent(
        targetState = day,
        transitionSpec = {
            val slideIntoDirection = if (initialState.isBefore(targetState)) Down else Up

            val enterAnim = slideIntoContainer(
                towards = slideIntoDirection,
            ).plus(fadeIn())

            val exitAnim = slideOutOfContainer(
                towards = slideIntoDirection,
            ).plus(fadeOut())

            enterAnim.togetherWith(exitAnim)
        },
        label = ""
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
            text = it.month.asMonthString(context),
        )
    }
}