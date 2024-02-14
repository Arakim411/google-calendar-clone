package com.arakim.googlecalendarclone.domain.user.signin.model

sealed interface SignInMethod {
    val id: SignInMethodId

    data class GoogleMethod(val accountName: String) : SignInMethod {
        override val id: SignInMethodId = SignInMethodId.Google
    }

    data object FakeMethod : SignInMethod {
        override val id: SignInMethodId = SignInMethodId.Fake
    }
}

enum class SignInMethodId {
    Google,
    Fake
}