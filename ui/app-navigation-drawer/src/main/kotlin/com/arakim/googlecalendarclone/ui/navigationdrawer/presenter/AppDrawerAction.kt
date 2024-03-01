package com.arakim.googlecalendarclone.ui.navigationdrawer.presenter

import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState

sealed interface AppDrawerAction {

    sealed interface InitializationAction : AppDrawerAction {
        data class InitializeAction(val userName: String) : InitializationAction
        data object InitializeFailedFailedAction : InitializationAction
    }

    data class CoreStateChangedAction(val newState: CoreDrawerState) : AppDrawerAction
}