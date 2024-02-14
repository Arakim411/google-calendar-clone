package com.arakim.googlecalendarclone.ui.screen.signin.presenter

import androidx.compose.runtime.Stable
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.SignInState.ReadyState
import com.arakim.googlecalendarclone.ui.screen.signin.presenter.reducer.SignInUserReducer
import com.arakim.googlecalendarclone.util.mvi.ReducerPresenterWithSideEffect
import javax.inject.Inject

internal typealias State = SignInState
internal typealias Action = SignInAction
internal typealias SideEffect = SignInSideEffect

@Stable
class SignInPresenter @Inject constructor(
    signInUserReducer: SignInUserReducer,
) : ReducerPresenterWithSideEffect<State, Action, SideEffect>(ReadyState) {

    init {
        registerReducer<SignInAction>(signInUserReducer)
    }
}