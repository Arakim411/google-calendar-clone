package com.arakim.googlecalendarclone.domain.user.signin.usecases

import com.arakim.googlecalendarclone.domain.user.signin.SignInService
import javax.inject.Inject

class GetCurrentUseUseCase @Inject constructor(
    private val signInService: SignInService,
) {
    operator fun invoke() = signInService.getCurrentUser()
}