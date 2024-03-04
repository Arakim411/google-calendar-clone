package com.arakim.googlecalendarclone.data.signin.common

import com.arakim.googlecaelndarclone.domain.user.SignInMethod
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult

interface SignInMethodService<T : SignInMethod> {
    suspend fun getAuthUser(method: T): TypedResult<AuthUser, CommonError>
    suspend fun getRefreshedAuthUser(user: AuthUser): TypedResult<AuthUser, CommonError>
}