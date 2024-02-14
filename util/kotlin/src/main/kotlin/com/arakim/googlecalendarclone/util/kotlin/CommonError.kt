package com.arakim.googlecalendarclone.util.kotlin

sealed interface CommonError {

    data object NoConnection : CommonError

    data object SslHandshakeError : CommonError

    data object NetworkError : CommonError

    data object UnknownError : CommonError

    data class OtherError(val message: String) : CommonError
}