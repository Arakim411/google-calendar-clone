@file:Suppress("TooManyFunctions")
package com.arakim.googlecalendarclone.util.kotlin

import com.arakim.googlecalendarclone.util.kotlin.TypedResult.Failure
import com.arakim.googlecalendarclone.util.kotlin.TypedResult.Success
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind.AT_MOST_ONCE
import kotlin.contracts.contract

sealed interface TypedResult<S, F> {

    data class Success<S, F>(val value: S) : TypedResult<S, F>

    data class Failure<S, F>(val reason: F) : TypedResult<S, F>

    companion object {
        inline fun <S, F> success(value: S): Success<S, F> = Success(value)

        inline fun <S, F> failure(reason: F): Failure<S, F> = Failure(reason)
    }
}

inline fun <S, F> TypedResult<S, F>.isSuccess() = this is Success<S, F>

inline fun <S, F> TypedResult<S, F>.isFailure() = this is Failure<S, F>

inline fun <S, F> TypedResult<S, F>.getOrNull(): S? =
    if (this is Success<S, F>) value else null

inline fun <S, F> TypedResult<S, F>.getOrThrow(): S =
    (this as Success<S, F>).value

inline fun <S, F> TypedResult<S, F>.getOrElse(onFailure: (reason: F) -> S): S =
    when (this) {
        is Success<S, F> -> value
        is Failure<S, F> -> onFailure(reason)
    }

inline fun <S, F> TypedResult<S, F>.getFailureReasonOrNull(): F? =
    if (this is Failure<S, F>) reason else null

inline fun <S, F> TypedResult<S, F>.getFailureReasonOrThrow(): F =
    (this as Failure<S, F>).reason

@OptIn(ExperimentalContracts::class)
inline fun <S, F> TypedResult<S, F>.onSuccess(action: (value: S) -> Unit): TypedResult<S, F> {
    contract {
        callsInPlace(action, AT_MOST_ONCE)
    }
    getOrNull()?.let { action(it) }
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <S, F> TypedResult<S, F>.onFailure(action: (value: F) -> Unit): TypedResult<S, F> {
    contract {
        callsInPlace(action, AT_MOST_ONCE)
    }
    getFailureReasonOrNull()?.let { action(it) }
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <S, F, R> TypedResult<S, F>.map(transform: (S) -> R): TypedResult<R, F> {
    contract {
        callsInPlace(transform, AT_MOST_ONCE)
    }
    return when {
        isSuccess() -> TypedResult.success(transform(getOrThrow()))
        else -> TypedResult.failure(getFailureReasonOrThrow())
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <S, F, R> TypedResult<S, F>.flatMap(transform: (S) -> TypedResult<R, F>): TypedResult<R, F> {
    contract {
        callsInPlace(transform, AT_MOST_ONCE)
    }
    return when {
        isSuccess() -> transform(getOrThrow())
        else -> TypedResult.failure(getFailureReasonOrThrow())
    }
}

@OptIn(ExperimentalContracts::class)
inline fun <S, F, R> TypedResult<S, F>.map(
    transformSuccess: (S) -> R,
    transformFailure: (F) -> R
): R {
    contract {
        callsInPlace(transformSuccess, AT_MOST_ONCE)
        callsInPlace(transformFailure, AT_MOST_ONCE)
    }
    return when {
        isSuccess() -> transformSuccess(getOrThrow())
        else -> transformFailure(getFailureReasonOrThrow())
    }
}
