package com.arakim.googlecalendarclone.data.signin.google

import com.arakim.googlecaelndarclone.domain.user.SignInMethod.GoogleMethod
import com.arakim.googlecaelndarclone.domain.user.SignInMethodId.Google
import com.arakim.googlecalendarclone.data.signin.common.AuthUser
import com.arakim.googlecalendarclone.data.signin.common.SignInMethodService
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.executeCommonIoAction
import com.arakim.googlecalendarclone.util.kotlin.getOrThrow
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoogleSignInMethodService @Inject constructor(
    private val credentialsHelper: GoogleAccountCredentialsHelper,
) : SignInMethodService<GoogleMethod> {

    override suspend fun getAuthUser(method: GoogleMethod): TypedResult<AuthUser, CommonError> =
        executeCommonIoAction {
            withContext(Dispatchers.IO) {
                val credentials = credentialsHelper.buildCredentials(method.accountName)
                AuthUser(
                    name = credentials.selectedAccountName,
                    methodId = method.id,
                    authToken = credentials.token
                )
            }
        }

    override suspend fun getRefreshedAuthUser(user: AuthUser): TypedResult<AuthUser, CommonError> =
        executeCommonIoAction {
            require(user.methodId == Google)
            getAuthUser(GoogleMethod(user.name)).getOrThrow()
        }
}