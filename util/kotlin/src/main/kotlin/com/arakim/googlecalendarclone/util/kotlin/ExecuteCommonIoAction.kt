package com.arakim.googlecalendarclone.util.kotlin

import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <T> executeCommonIoAction(
    noinline action: suspend () -> T,
): TypedResult<T, CommonError> = try {
    TypedResult.success(action())
} catch (e: CancellationException) {
    throw e
} catch (t: Throwable) {
    TypedResult.failure(CommonError.OtherError(t.message ?: "Unknown error"))
}