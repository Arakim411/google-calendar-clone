package com.arakim.googlecalendarclone.util.kotlin

import com.arakim.googlecalendarclone.util.kotlin.CommonError.NetworkError
import com.arakim.googlecalendarclone.util.kotlin.CommonError.OtherError
import com.arakim.googlecalendarclone.util.kotlin.CommonError.SslHandshakeError
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <S> executeCommonNetworkCall(noinline call: suspend () -> S): TypedResult<S, CommonError> =
    try {
        TypedResult.success(call())
    } catch (e: CancellationException) {
        throw e
    } catch (e: SSLHandshakeException) {
        TypedResult.failure(SslHandshakeError)
    } catch (e: SocketTimeoutException) {
        TypedResult.failure(NetworkError)
    } catch (t: Throwable) {
        TypedResult.failure(OtherError(t))
    }
