package com.arakim.googlecalendarclone.ui.screen.signin.compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.googlecalendarclone.ui.common.CommonErrorView
import com.arakim.googlecalendarclone.ui.common.CommonLoaderView
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.NavigationAction
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.toAction
import com.arakim.googlecalendarclone.ui.screen.signin.SignInViewModel
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction.SignInUserWithFake
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInAction.SignInUserWithGoogle
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInSideEffect.SignedInSideEffect
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.ErrorState
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.ReadyState
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.SigningInState
import com.arakim.googlecalendarclone.ui.screen.signin.rememberPickAccountHelper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

// TODO navigation
// TODO different screen sizes
// correct support dark theme
@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel<SignInViewModel>(),
    navigate: (NavigationAction) -> Unit,
) {

    val presenter = viewModel.presenter
    val state = presenter.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        presenter.sideEffectFlow.onEach {
            when (it) {
                is SignedInSideEffect -> navigate(MainDestination.HomeDestination.toAction())
            }
        }.launchIn(this)
    }

    Crossfade(targetState = state.value, label = "") { stateValue ->
        when (stateValue) {
            is ErrorState -> CommonErrorView()
            ReadyState -> ReadyState(onAction = presenter::onAction)
            SigningInState -> CommonLoaderView()
        }
    }
}

@Composable
private fun ReadyState(
    onAction: (SignInAction) -> Unit
) {
    val pickAccountHelper = rememberPickAccountHelper(
        onAccountPicked = { account ->
            onAction(SignInUserWithGoogle(account))
        },
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SignInWithGoogleButton(
            onClick = {
                pickAccountHelper.pickAccount()
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        SignInWithFakeButton(onClick = { onAction(SignInUserWithFake) })
    }
}