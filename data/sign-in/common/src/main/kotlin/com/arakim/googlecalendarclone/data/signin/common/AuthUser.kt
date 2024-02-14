package com.arakim.googlecalendarclone.data.signin.common

import com.arakim.googlecalendarclone.domain.user.signin.model.SignInMethodId

data class AuthUser(
    val name: String,
    val methodId: SignInMethodId,
    val authToken: String,
)

fun AuthUser.toJson(): String = toDto().toJson()
fun authUserFromJson(json: String): AuthUser = authUserDtoFromJson(json).toAuthUser()
