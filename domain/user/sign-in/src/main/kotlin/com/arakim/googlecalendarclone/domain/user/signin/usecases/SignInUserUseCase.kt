package com.arakim.googlecalendarclone.domain.user.signin.usecases

import com.arakim.googlecaelndarclone.domain.user.SignInMethod
import com.arakim.googlecalendarclone.domain.user.signin.SignInService
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(
    private val signInService: SignInService
) {
    suspend operator fun invoke(provider: SignInMethod) = signInService.signIn(provider)
}
