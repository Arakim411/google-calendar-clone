package com.arakim.googlecalendarclone.data.auth.fake

import com.arakim.googlecalendarclone.data.signin.common.AuthUser
import com.arakim.googlecalendarclone.data.signin.common.SignInMethodService
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethod.FakeMethod
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethodId.Fake
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import javax.inject.Inject

class FakeSignInMethodService @Inject constructor() : SignInMethodService<FakeMethod> {
    override suspend fun getAuthUser(method: FakeMethod): TypedResult<AuthUser, CommonError> {
        return TypedResult.success(
            AuthUser(
                name = "fake user",
                authToken = "fake auth token",
                methodId = Fake,
            )
        )
    }
}