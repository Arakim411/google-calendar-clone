package com.arakim.googlecalendarclone.data.signin

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.arakim.googlecalendarclone.data.signin.common.AuthUser
import com.arakim.googlecalendarclone.data.signin.common.authUserFromJson
import com.arakim.googlecalendarclone.data.signin.common.toJson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthUserRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    val authUser get() = preferences.getString(AuthUserKey, null)?.let { authUserFromJson(it) }

    private var preferences = context.getSharedPreferences(PrefKey, Context.MODE_PRIVATE)

    fun saveUser(authUser: AuthUser) {
        preferences.edit().putString(AuthUserKey, authUser.toJson()).apply()
    }

    fun clearUser() {
        preferences.edit().remove(AuthUserKey).apply()
    }

    @VisibleForTesting
    companion object {
        const val PrefKey = "pref_auth"
        const val AuthUserKey = "auth_user"
    }
}