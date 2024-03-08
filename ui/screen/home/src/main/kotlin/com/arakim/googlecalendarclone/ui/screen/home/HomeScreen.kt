package com.arakim.googlecalendarclone.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.arakim.googlecalendarclone.ui.calendar.compose.CalendarView
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializeAction

// TODO handle window size class
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    changeIsDrawerOpen: (Boolean) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.calendarPresenter.onAction(InitializeAction)
    }

    Scaffold(
        topBar = { TopBar(onNavigateIconClick = { changeIsDrawerOpen(true) }) },
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
) {
    TopAppBar(
        title = { Text("Home/TODO") },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable(onClick = onNavigateIconClick),
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
            )
        },
    )
}
