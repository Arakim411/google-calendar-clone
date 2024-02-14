package com.arakim.googlecalendarclone.data.signin.google

import android.content.Context
import com.arakim.googlecalendarclone.data.signin.common.AuthUser
import com.arakim.googlecalendarclone.data.signin.common.SignInMethodService
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethod.GoogleMethod
import com.arakim.googlecalendarclone.util.kotlin.CommonError
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.executeCommonIoAction
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.CalendarScopes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoogleSignInMethodService @Inject constructor(
    @ApplicationContext private val context: Context
) : SignInMethodService<GoogleMethod> {

    private val credentials: GoogleAccountCredential = GoogleAccountCredential.usingOAuth2(
        context,
        arrayListOf(CalendarScopes.CALENDAR)
    ).setBackOff(ExponentialBackOff())

    override suspend fun getAuthUser(method: GoogleMethod): TypedResult<AuthUser, CommonError> =
        executeCommonIoAction {
            withContext(Dispatchers.IO) {
                credentials.selectedAccountName = method.accountName
                AuthUser(
                    name = credentials.selectedAccountName,
                    methodId = method.id,
                    authToken = credentials.token
                )
            }
        }
}