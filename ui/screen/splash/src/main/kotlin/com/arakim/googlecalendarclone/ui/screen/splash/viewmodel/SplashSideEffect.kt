package com.arakim.googlecalendarclone.ui.screen.splash.viewmodel

sealed interface SplashSideEffect {

    data object UserSignedInSideEffect : SplashSideEffect
    data object UserNotSignedInSideEffect : SplashSideEffect
}