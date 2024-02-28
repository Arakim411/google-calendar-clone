package com.arakim.googlecalendarclone.ui.navigationdrawer

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.CoreNavigationDrawer
import com.arakim.googlecalendarclone.ui.appnavigationdrawer.R

@Composable
fun AppNavigationDrawerView(
    content: @Composable () -> Unit,
) {
    val viewModel = hiltViewModel<AppDrawerViewModel>()

    CoreNavigationDrawer(
        corePresenter = viewModel.appDrawerPresenter.corePresenter,
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