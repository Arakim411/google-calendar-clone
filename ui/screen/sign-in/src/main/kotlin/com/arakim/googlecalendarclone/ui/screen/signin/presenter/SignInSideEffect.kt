package com.arakim.googlecalendarclone.ui.screen.signin.presenter

import com.arakim.googlecaelndarclone.domain.user.User

sealed interface SignInSideEffect {

    data class SignedInSideEffect(val user: User) : SignInSideEffect
}