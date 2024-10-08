package com.arakim.googlecalendarclone.data.auth.fake

import com.arakim.googlecaelndarclone.domain.user.SignInMethod.FakeMethod
import com.arakim.googlecaelndarclone.domain.user.SignInMethodId.Fake
import com.arakim.googlecalendarclone.data.signin.common.AuthUser
import com.arakim.googlecalendarclone.data.signin.common.SignInMethodService
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

    override suspend fun getRefreshedAuthUser(user: AuthUser): TypedResult<AuthUser, CommonError> {
        return getAuthUser(FakeMethod)
    }
}