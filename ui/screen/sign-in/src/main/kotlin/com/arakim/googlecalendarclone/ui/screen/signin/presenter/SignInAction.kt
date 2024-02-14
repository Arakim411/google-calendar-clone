package com.arakim.googlecalendarclone.ui.screen.signin.presenter

import com.arakim.googlecalendarclone.util.kotlin.CommonError

sealed interface SignInAction {

    data class SignInUserWithGoogle(val accountName: String) : SignInAction
    data object SignInUserWithFake : SignInAction

    data class SignedInErrorAction(val error: CommonError) : SignInAction
}