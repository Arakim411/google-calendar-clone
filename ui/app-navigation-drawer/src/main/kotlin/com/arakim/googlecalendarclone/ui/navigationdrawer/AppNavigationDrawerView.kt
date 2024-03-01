package com.arakim.googlecalendarclone.ui.navigationdrawer

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.CoreNavigationDrawer
import com.arakim.googlecalendarclone.ui.appnavigationdrawer.R

@Composable
fun AppNavigationDrawerView(
    viewModel: AppDrawerViewModel = hiltViewModel<AppDrawerViewModel>(),
    drawerState: DrawerState = rememberDrawerState(initialValue = Closed),
    content: @Composable () -> Unit,
) {

    CoreNavigationDrawer(
        corePresenter = viewModel.appDrawerPresenter.corePresenter,
        drawerState = drawerState,
        content = content,
        title = {
            Icon(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    )
}