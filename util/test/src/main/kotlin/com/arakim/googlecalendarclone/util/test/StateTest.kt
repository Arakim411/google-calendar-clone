package com.arakim.googlecalendarclone.util.test

import kotlin.reflect.KClass
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

abstract class StateTest {

    @Suppress("MemberNameEqualsClassName")
    inline fun <reified T : Any> stateTest(
        vararg states: KClass<out T>,
        testBody: (T) -> Unit
    ) {
        states.forEach {
            when {
                it.objectInstance != null -> testBody(it.objectInstance!!)
                else -> mockkByClass(it)
            }
        }
    }

    inline fun <reified T : Any> runTestState(
        vararg states: KClass<out T>,
        crossinline testBody: suspend TestScope.(T) -> Unit
    ) = runTest {
        stateTest(
            states = states,
            testBody = { testBody(it) }
        )
    }

    inline fun <reified T : Any> runTestState(
        states: List<KClass<out T>>,
        crossinline testBody: suspend TestScope.(T) -> Unit
    ) = runTest {
        stateTest(
            states = states.toTypedArray(),
            testBody = { testBody(it) }
        )
    }
}
