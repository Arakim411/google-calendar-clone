package com.arakim.googlecalendarclone.ui.screen.splash.viewmodel

import com.arakim.googlecalendarclone.util.kotlin.CommonError

sealed interface SplashState {

    data object ReadyState : SplashState
    data object SigningInState : SplashState
    data class ErrorState(val error: CommonError) : SplashState
}