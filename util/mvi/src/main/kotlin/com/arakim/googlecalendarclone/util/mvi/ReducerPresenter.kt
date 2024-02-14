package com.arakim.googlecalendarclone.util.mvi

import kotlin.reflect.KClass

abstract class ReducerPresenter<State, Action>(initialState: State) :
    Presenter<State, Action>(initialState) where State : Any, Action : Any {

    internal val reducers = mutableMapOf<KClass<out Action>, StateReducer<State, Action, out Action>>()

    override fun onInitialized() {
        super.onInitialized()
        reducers.values.forEach {
            it.initialize(
                coroutineScope = coroutineScope,
                onAction = this::onAction,
                logInvalidState = { logInvalidState() }
            )
        }
    }

    protected inline fun <reified T : Action> registerReducer(
        reducer: StateReducer<State, Action, out Action>,
    ) {
        registerReducer(
            reducer = reducer,
            actionClass = T::class
        )
    }

    protected fun registerReducer(
        reducer: StateReducer<State, Action, out Action>,
        actionClass: KClass<out Action>
    ) {
        require(!isInitialized)
        reducers[actionClass] = reducer
    }

    override fun State.reduce(action: Action): State {
        val reducer = reducers.firstNotNullOf { (actionClass, reducer) ->
            if (actionClass.isInstance(action)) {
                reducer
            } else {
                null
            }
        }

        return reducer.reduce(this, action)
    }
}