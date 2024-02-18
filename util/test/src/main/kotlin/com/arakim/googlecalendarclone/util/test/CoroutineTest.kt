@file:OptIn(ExperimentalCoroutinesApi::class)

package com.arakim.googlecalendarclone.util.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

open class CoroutineTest : StateTest() {

    lateinit var coroutineScope: CoroutineScope
        private set

    private lateinit var dispatcher: TestDispatcher

    @BeforeEach
    fun before() {
        dispatcher = StandardTestDispatcher()
        coroutineScope = CoroutineScope(dispatcher)
        Dispatchers.setMain(dispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
