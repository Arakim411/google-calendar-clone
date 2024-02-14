package com.arakim.googlecaelndarclone.domain.user

sealed interface User {
    data object AnonymousUser : User
    data class SignedUser(val name: String) : User
}