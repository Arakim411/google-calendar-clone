package com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter

import androidx.compose.runtime.Immutable
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerAction.ItemClickedAction
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerAction.ReleaseAction
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerAction.SetGroupsAction
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState.IdleState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.reducer.ItemSelectedReducer
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.reducer.ReleaseDrawerReducer
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.reducer.SetGroupsReducer
import com.arakim.googlecalendarclone.util.mvi.ReducerPresenterWithSideEffect
import javax.inject.Inject

typealias State = CoreDrawerState
typealias SideEffect = CoreDrawerSideEffect
typealias Action = CoreDrawerAction

@Immutable
class CoreNavigationDrawerPresenter @Inject constructor(
    setGroupsReducer: SetGroupsReducer,
    itemSelectedReducer: ItemSelectedReducer,
    releaseReducer: ReleaseDrawerReducer,
) : ReducerPresenterWithSideEffect<State, Action, SideEffect>(IdleState) {

    init {
        registerReducer<SetGroupsAction>(setGroupsReducer)
        registerReducer<ItemClickedAction>(itemSelectedReducer)
        registerReducer<ReleaseAction>(releaseReducer)
    }
}