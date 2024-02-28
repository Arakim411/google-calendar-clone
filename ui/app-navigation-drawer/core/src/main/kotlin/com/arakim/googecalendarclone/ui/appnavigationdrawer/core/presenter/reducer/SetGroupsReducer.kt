package com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.reducer

import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.Action
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerAction.SetGroupsAction
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState.IdleState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState.ReadyState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.State
import com.arakim.googlecalendarclone.util.mvi.StateReducer
import javax.inject.Inject

class SetGroupsReducer @Inject constructor() : StateReducer<State, Action, SetGroupsAction>() {
    override fun State.reduce(action: SetGroupsAction): State = when (this) {
        IdleState, is ReadyState -> reduceSetGroupsAction(action)
    }

    private fun reduceSetGroupsAction(action: SetGroupsAction): State {
        return ReadyState(action.groups)
    }
}