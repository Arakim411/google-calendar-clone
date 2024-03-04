package com.arakim.googlecalendarclone.data.signin.common

import com.arakim.googlecaelndarclone.domain.user.SignInMethodId
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

internal data class AuthUserDto(
    val name: String,
    val methodId: String,
    val authToken: String,
)

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(AuthUserDto::class.java)

internal fun AuthUserDto.toJson(): String = moshi.toJson(this)
internal fun authUserDtoFromJson(json: String): AuthUserDto = moshi.fromJson(json)!!

internal fun AuthUser.toDto(): AuthUserDto {
    return AuthUserDto(
        name = name,
        methodId = methodId.name,
        authToken = authToken,
    )
}

internal fun AuthUserDto.toAuthUser() = AuthUser(
    name = name,
    methodId = SignInMethodId.valueOf(methodId),
    authToken = authToken,
)
