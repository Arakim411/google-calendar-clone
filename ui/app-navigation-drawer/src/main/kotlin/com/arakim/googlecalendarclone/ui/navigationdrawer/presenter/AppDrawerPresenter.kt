package com.arakim.googlecalendarclone.ui.navigationdrawer.presenter

import androidx.compose.runtime.Immutable
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreNavigationDrawerPresenter
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerState.IdleState
import com.arakim.googlecalendarclone.util.mvi.ReducerPresenterWithSideEffect
import javax.inject.Inject

typealias State = AppDrawerState
typealias Action = AppDrawerAction
typealias SideEffect = AppDrawerSideEffect

@Immutable
class AppDrawerPresenter @Inject constructor(
    val corePresenter: CoreNavigationDrawerPresenter,
) : ReducerPresenterWithSideEffect<State, Action, SideEffect>(IdleState) {

    override fun onInitialized() {
        super.onInitialized()
        corePresenter.initialize(coroutineScope)
    }
}