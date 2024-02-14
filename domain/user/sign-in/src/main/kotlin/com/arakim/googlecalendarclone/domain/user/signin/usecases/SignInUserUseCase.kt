package com.arakim.googlecalendarclone.domain.user.signin.usecases

import com.arakim.googlecalendarclone.domain.user.signin.SignInService
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethod
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(
    private val signInService: SignInService
) {
    suspend operator fun invoke(provider: SignInMethod) = signInService.signIn(provider)
}
