package com.arakim.googlecalendarclone.data.calendarsetup

import android.content.Context
import com.arakim.googlecalendarclone.data.calendarsetup.model.CalendarSetUpDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class CalendarSetUpLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val preferences = context.getSharedPreferences(PrefKey, Context.MODE_PRIVATE)
    private val moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(CalendarSetUpDto::class.java)

    fun getSetUp(): CalendarSetUpDto? {
        val json = preferences.getString(PrefKey, null) ?: return null
        return moshi.fromJson(json)
    }

    fun saveSetUp(calendarSetUpDto: CalendarSetUpDto) {
        preferences.edit().putString(PrefKey, moshi.toJson(calendarSetUpDto)).apply()
    }

    private companion object {
        private const val PrefKey = "calendar_setup"
    }
}