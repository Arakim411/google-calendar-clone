package com.arakim.googlecalendarclone.data.signin

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.arakim.googlecalendarclone.data.signin.common.AuthUser
import com.arakim.googlecalendarclone.data.signin.common.toJson
import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethodId
import com.arakim.googlecalendarclone.util.test.randomString
import org.junit.Before
import org.junit.Test

class AuthUserRepositoryTest {

    lateinit var subject: AuthUserRepository
    lateinit var context: Context
    lateinit var pref: SharedPreferences

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        subject = AuthUserRepository(context)

        pref = context.getSharedPreferences(AuthUserRepository.PrefKey, Context.MODE_PRIVATE)
        pref.edit().clear().apply()
    }

    @Test
    fun initial_user_is_null() {
        assertThat(subject.authUser).isEqualTo(null)
        assertThat(pref.getString(AuthUserRepository.AuthUserKey, null))
    }

    @Test
    fun when_save_is_invoked_then_save_user_in_shared_pref() {
        val user = fakeAuthUser()
        subject.saveUser(user)

        assertThat(pref.getString(AuthUserRepository.AuthUserKey, null)).isNotNull()
        assertThat(pref.getString(AuthUserRepository.AuthUserKey, null)).isEqualTo(user.toJson())
        assertThat(subject.authUser).isEqualTo(user)
    }

    @Test
    fun when_clear_is_invoked_then_clear_user_in_shared_pref() {
        val user = fakeAuthUser()
        subject.saveUser(user)
        subject.clearUser()

        assertThat(pref.getString(AuthUserRepository.AuthUserKey, null)).isEqualTo(null)
        assertThat(subject.authUser).isEqualTo(null)
    }

    private fun fakeAuthUser(
        name: String = randomString(),
        methodId: SignInMethodId = SignInMethodId.entries.random(),
        authToken: String = randomString()
    ): AuthUser = AuthUser(name, methodId, authToken)
}