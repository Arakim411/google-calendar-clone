package com.arakim.googlecalendarclone.data.calendarinfo.google.remote

import com.arakim.googlecalendarclone.data.signin.AuthUserRepository
import com.arakim.googlecalendarclone.data.signin.google.GoogleAccountCredentialsHelper
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.calendar.Calendar
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccessCalendarHelper @Inject constructor(
    private val googleAccountCredentialsHelper: GoogleAccountCredentialsHelper,
    private val authUserRepository: AuthUserRepository,
) {
    private lateinit var calendar: Calendar
    private var calendarAccountName: String? = null

    suspend fun <T> accessCalendar(scope: Calendar.() -> T): T =
        withContext(Dispatchers.IO) {
            if (calendarAccountName != authUserRepository.authUser?.name) {
                initializeCalendar()
            }
            return@withContext calendar.scope()
        }

    private suspend fun initializeCalendar() {
        val accountName = authUserRepository.authUser?.name!!
        calendarAccountName = accountName
        calendar = buildCalendar(accountName)
    }

    private fun buildCalendar(accountName: String): Calendar {
        val transport = AndroidHttp.newCompatibleTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()
        val credentialsHelper = googleAccountCredentialsHelper.buildCredentials(accountName)

        return Calendar
            .Builder(transport, jsonFactory, credentialsHelper)
            .setApplicationName("Google calendar clone")
            .build()
    }
}