package com.arakim.googlecalendarclone.data.signin.google

import android.content.Context
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.calendar.CalendarScopes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleAccountCredentialsHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun buildCredentials(accountName: String): GoogleAccountCredential {
        val credentials: GoogleAccountCredential = GoogleAccountCredential.usingOAuth2(
            context,
            arrayListOf(CalendarScopes.CALENDAR)
        ).setBackOff(ExponentialBackOff()).apply {
            selectedAccountName = accountName
        }

        return credentials
    }
}