package com.arakim.googlecalendarclone.util.mvi

import kotlinx.coroutines.CoroutineScope

abstract class StateReducerWithSideEffect<StateType, ActionType, MyActionType, SideEffectType> :
    StateReducer<StateType, ActionType, MyActionType>() where MyActionType : ActionType {

    lateinit var emitSideEffect: (SideEffectType) -> Unit
        internal set

    fun initialize(
        coroutineScope: CoroutineScope,
        onAction: (ActionType) -> Unit,
        logInvalidState: StateType.() -> StateType,
        emitSideEffect: (SideEffectType) -> Unit,
    ) {
        this.emitSideEffect = emitSideEffect
        initialize(
            coroutineScope = coroutineScope,
            onAction = onAction,
            logInvalidState = logInvalidState,
        )
    }
}