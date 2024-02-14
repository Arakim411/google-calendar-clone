package com.arakim.googlecalendarclone.domain.user.signin.usecases

import com.arakim.googlecalendarclone.domain.user.signin.SignInService
import javax.inject.Inject

class UserFlowUseCase @Inject constructor(
    private val signInService: SignInService,
) {
    operator fun invoke() = signInService.userFlow()
}