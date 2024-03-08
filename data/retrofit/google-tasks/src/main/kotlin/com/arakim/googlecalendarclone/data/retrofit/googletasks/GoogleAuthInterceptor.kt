package com.arakim.googlecalendarclone.data.retrofit.googletasks

import android.security.keystore.UserNotAuthenticatedException
import com.arakim.googlecalendarclone.data.signin.AuthUserRepository
import com.arakim.googlecalendarclone.data.signin.SignInServiceImpl
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class GoogleAuthInterceptor @Inject constructor(
    private val authUserRepository: AuthUserRepository,
    private val signInServiceImpl: SignInServiceImpl,
) : Interceptor {

    override fun intercept(chain: Chain): Response {
        val response = chain.proceed(chain.request().addTokenHeader(getAuthToken()))

        return if (response.isAuthorization) {
            response
        } else {
            refreshTokenAndRetry(chain)
        }
    }

    private fun getAuthToken(): String =
        authUserRepository.authUser?.authToken
            ?: throw UserNotAuthenticatedException("can't obtain auth token")

    private fun refreshTokenAndRetry(chain: Chain): Response {
        val refreshedToken = getRefreshedToken()
        val response = chain.proceed(chain.request().addTokenHeader(refreshedToken))
        if (response.isAuthorization) {
            return response
        } else {
            throw UserNotAuthenticatedException("can't obtain auth token")
        }
    }

    // TODO auth sure should has it's own repository where we are able to refresh him
    private fun getRefreshedToken(): String = synchronized(this) {
        runBlocking {
            signInServiceImpl.getRefreshedUser()
            getAuthToken()
        }
    }

    private fun Request.addTokenHeader(token: String): Request = newBuilder()
        .header("Authorization", "Bearer $token")
        .build()

    private val Response.isAuthorization: Boolean
        get() = code != NOT_AUTHORIZED
}

private const val NOT_AUTHORIZED = 401