package com.arakim.googlecalendarclone.util.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

inline fun CoroutineScope.yielded(
    crossinline action: suspend () -> Unit
): Job = launch {
    yield()
    action()
}