package com.arakim.googlecalendarclone.util.mvi

import kotlinx.coroutines.CoroutineScope

abstract class StateReducer<State, Action, ActionToReduce : Action> {

    protected lateinit var coroutineScope: CoroutineScope
        private set
    protected lateinit var onAction: (Action) -> Unit
        private set
    protected lateinit var logInvalidState: State.() -> State

    fun initialize(
        coroutineScope: CoroutineScope,
        onAction: (Action) -> Unit,
        logInvalidState: State.() -> State,
    ) {
        this.coroutineScope = coroutineScope
        this.onAction = onAction
        this.logInvalidState = logInvalidState
        onInitialized()
    }

    protected open fun onInitialized() = Unit

    abstract fun State.reduce(action: ActionToReduce): State

    internal fun reduce(state: State, action: Action) = state.reduce(action as ActionToReduce)
}