package com.arakim.googlecalendarclone.domain.user.signin

import com.arakim.googlecaelndarclone.domain.user.User
import com.arakim.googlecaelndarclone.domain.user.User.SignedUser
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethod
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import kotlinx.coroutines.flow.Flow

typealias UserResult = TypedResult<User, CommonError>
typealias SignedUserResult = TypedResult<SignedUser, CommonError>

interface SignInService {
    suspend fun signIn(method: SignInMethod): SignedUserResult
    fun getLocalUser(): UserResult
    suspend fun getRefreshedUser(): UserResult
    fun userFlow(): Flow<UserResult>
    fun signOut()
}