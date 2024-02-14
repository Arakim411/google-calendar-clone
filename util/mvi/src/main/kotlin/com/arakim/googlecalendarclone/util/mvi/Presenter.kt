package com.arakim.googlecalendarclone.util.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class Presenter<State, Action>(initialState: State) {

    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow()

    protected lateinit var coroutineScope: CoroutineScope
        private set
    val isInitialized get() = ::coroutineScope.isInitialized

    fun initialize(coroutineScope: CoroutineScope) {
        this.coroutineScope = coroutineScope
        onInitialized()
    }

    protected open fun onInitialized() = Unit

    protected abstract fun State.reduce(action: Action): State

    fun onAction(action: Action) {
        val currentState = _stateFlow.value
        _stateFlow.value = currentState.reduce(action)
    }

    protected fun State.logInvalidState(): State {
        return this
    }
}