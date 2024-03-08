package com.arakim.googlecalendarclone.ui.screen.signin.presenter

import com.arakim.googlecaelndarclone.domain.user.User
import com.google.android.gms.auth.UserRecoverableAuthException

sealed interface SignInSideEffect {

    data class SignedInSideEffect(val user: User) : SignInSideEffect
    data class UserRecoverableAuthExceptionSideEffect(
        val exception: UserRecoverableAuthException,
    ) : SignInSideEffect
}