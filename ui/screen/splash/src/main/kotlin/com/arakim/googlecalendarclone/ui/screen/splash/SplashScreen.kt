package com.arakim.googlecalendarclone.ui.screen.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.googlecalendarclone.ui.common.CommonErrorView
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination.HomeDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination.SignInDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.NavigationAction
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.toAction
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashScreenViewModel
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashSideEffect.UserNotSignedInSideEffect
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashSideEffect.UserSignedInSideEffect
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashState.ErrorState
import com.arakim.googlecalendarclone.ui.screen.splash.viewmodel.SplashState.SigningInState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel = hiltViewModel(),
    navigate: (NavigationAction) -> Unit,
    showNativeSplash: MutableState<Boolean>,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.sideEffectFlow.onEach {
            when (it) {
                is UserNotSignedInSideEffect -> navigate(SignInDestination.toAction(removeAllDestinations = true))
                is UserSignedInSideEffect -> navigate(HomeDestination.toAction(removeAllDestinations = true))
            }
        }.launchIn(this)

        viewModel.tryAutoSignIn()
    }

    LaunchedEffect(Unit) {
        viewModel.state.onEach {
            when (it) {
                SigningInState -> showNativeSplash.value = true
                else -> showNativeSplash.value = false
            }
        }.launchIn(this)
    }

    DisposableEffect(Unit) {
        onDispose {
            showNativeSplash.value = false
        }
    }

    when (state) {
        is ErrorState -> CommonErrorView(
            commonError = state.error,
            onRetry = { viewModel.tryAutoSignIn() }
        )

        else -> Unit
    }
}