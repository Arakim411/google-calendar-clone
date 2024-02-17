package com.arakim.googlecalendarclone.domain.user.signin.usecases

import com.arakim.googlecalendarclone.domain.user.signin.SignInService
import javax.inject.Inject

class GetRefreshedUserUseCase @Inject constructor(
    private val signInService: SignInService
) {
    suspend operator fun invoke() = signInService.getRefreshedUser()
}