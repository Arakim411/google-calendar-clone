package com.arakim.googlecalendarclone.domain.user.signin.model

import com.arakim.googlecaelndarclone.domain.user.SignInMethodId
import dagger.MapKey

@MapKey
annotation class SignedUserTypeKey(val value: SignInMethodId)