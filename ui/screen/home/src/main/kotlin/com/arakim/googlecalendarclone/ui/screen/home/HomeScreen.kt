package com.arakim.googlecalendarclone.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerState.ErrorState
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerState.IdleState
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerState.InitializingState
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerState.ReadyState
import com.arakim.googlecalendarclone.util.compose.windowsizeclass.ImpactProperty
import com.arakim.googlecalendarclone.util.compose.windowsizeclass.WindowSizeDelimiter
import com.arakim.googlecalendarclone.util.compose.windowsizeclass.WindowSizeType.Compact
import com.arakim.googlecalendarclone.util.compose.windowsizeclass.WindowSizeType.Expanded
import com.arakim.googlecalendarclone.util.compose.windowsizeclass.WindowSizeType.Medium

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    changeIsDrawerOpen: (Boolean) -> Unit,
) {

    val state = viewModel.appDrawerPresenter.stateFlow.collectAsStateWithLifecycle().value
    val lastSideEffect = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.appDrawerPresenter.sideEffectFlow.collect {
            lastSideEffect.value = it::class.simpleName ?: "unknown"
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        WindowSizeDelimiter(impactedProperty = ImpactProperty.Height) {
            when (it) {
                Compact -> Text(text = "compact")
                Medium -> Text(text = "medium")
                Expanded -> Text(text = "expanded")
            }
        }

        Button(onClick = { viewModel.signOut() }) {
            Text(text = "sign out")
        }

        Button(onClick = { changeIsDrawerOpen(true) }) {
            Text(text = "open drawer")
        }

        Text(text = "last side effect: ${lastSideEffect.value}")

        when (state) {
            ErrorState -> Text(text = "errorState")
            IdleState -> Text(text = "idleState")
            InitializingState -> Text(text = "initializingState")
            is ReadyState -> ReadyState(readyState = state)
        }
    }
}

@Composable
private fun ColumnScope.ReadyState(readyState: ReadyState) {
    Text(text = "type: ${readyState.calendarRangeType.name}")
    Text(text = "isEventChecked: ${readyState.isEventsChecked}")
    Text(text = "isTasksChecked: ${readyState.isTasksChecked}")
    Text(text = "isBirthDayChecked: ${readyState.isBirthdaysChecked}")
    Text(text = "isHolidaysChecked: ${readyState.isHolidaysChecked}")
}
