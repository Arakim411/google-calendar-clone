package com.arakim.googlecaelndarclone.domain.user

import com.arakim.googlecaelndarclone.domain.user.SignInMethodId.Fake
import com.arakim.googlecaelndarclone.domain.user.SignInMethodId.Google

sealed interface SignInMethod {
    val id: SignInMethodId

    data class GoogleMethod(val accountName: String) : SignInMethod {
        override val id: SignInMethodId = Google
    }

    data object FakeMethod : SignInMethod {
        override val id: SignInMethodId = Fake
    }
}

enum class SignInMethodId {
    Google,
    Fake
}