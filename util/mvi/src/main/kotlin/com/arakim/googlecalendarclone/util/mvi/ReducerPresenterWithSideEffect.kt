package com.arakim.googlecalendarclone.util.mvi

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@Suppress("MaxLineLength")
abstract class ReducerPresenterWithSideEffect<StateType, ActionType, SideEffectType>(
    initialState: StateType,
) : ReducerPresenter<StateType, ActionType>(initialState) where StateType : Any, ActionType : Any, SideEffectType : Any {

    private val _sideEffectFlow = MutableSharedFlow<SideEffectType>(extraBufferCapacity = 1)
    val sideEffectFlow = _sideEffectFlow.asSharedFlow()

    override fun onInitialized() {
        reducers.forEach { (_, reducer) ->
            if (reducer is StateReducerWithSideEffect<StateType, ActionType, *, *>) {
                reducer.initialize(
                    coroutineScope = coroutineScope,
                    onAction = ::onAction,
                    logInvalidState = { logInvalidState() },
                    emitSideEffect = {
                        @Suppress("UNCHECKED_CAST")
                        emitSideEffect(it as SideEffectType)
                    },
                )
            } else {
                reducer.initialize(
                    coroutineScope = coroutineScope,
                    onAction = ::onAction,
                    logInvalidState = { logInvalidState() },
                )
            }
        }
    }

    protected inline fun <reified T : ActionType> registerReducer(
        reducer: StateReducerWithSideEffect<StateType, ActionType, out ActionType, SideEffectType>,
    ) {
        registerReducer(reducer, T::class)
    }

    protected fun emitSideEffect(sideEffectType: SideEffectType) {
        coroutineScope.launch {
            _sideEffectFlow.emit(sideEffectType)
        }
    }
}