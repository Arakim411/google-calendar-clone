@file:Suppress("NOTHING_TO_INLINE")

package com.arakim.googlecalendarclone.util.test

import io.mockk.MockKGateway
import kotlin.reflect.KClass

@Suppress("NO_REFLECTION_IN_CLASS_PATH")
fun <T : Any> KClass<T>.allSealedSubObjectsOrMocks(): List<T> =
    sealedSubclasses.flatMap {
        when {
            it.objectInstance != null -> listOf(it.objectInstance!!)
            it.isSealed -> it.sealedSubclasses.map { sealed ->
                sealed.objectInstance ?: mockkByClass(sealed)
            }

            else -> listOf(mockkByClass(it))
        }
    }

@Suppress("NO_REFLECTION_IN_CLASS_PATH")
fun <T : Any> KClass<T>.allDerivedSealedClassesRecursive(): List<KClass<out T>> =
    sealedSubclasses.flatMap {
        when {
            it.isSealed -> listOf(it) + it.allDerivedSealedClassesRecursive()
            else -> listOf(it)
        }
    }

inline fun <T : Any> mockkByClass(
    mockType: KClass<T>,
    name: String? = null,
    relaxed: Boolean = false,
    vararg moreInterfaces: KClass<*>,
    relaxUnitFun: Boolean = false,
    block: T.() -> Unit = {}
): T {
    val mock = MockKGateway.implementation().mockFactory.mockk(
        mockType,
        name,
        relaxed,
        moreInterfaces,
        relaxUnitFun
    )
    block(mock)
    return mock
}
