package com.arakim.googlecalendarclone.ui.screen.signin.presenter

import androidx.compose.runtime.Immutable
import com.arakim.googlecalendarclone.util.kotlin.CommonError

@Immutable
sealed interface SignInState {

    data object ReadyState : SignInState

    data class SigningInState(val action: SignInAction) : SignInState

    data class ErrorState(val error: CommonError) : SignInState
}