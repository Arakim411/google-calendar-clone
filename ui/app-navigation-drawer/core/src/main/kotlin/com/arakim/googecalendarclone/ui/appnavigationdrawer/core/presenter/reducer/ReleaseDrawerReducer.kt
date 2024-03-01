package com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.reducer

import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.Action
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerAction.ReleaseAction
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState.IdleState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.State
import com.arakim.googlecalendarclone.util.mvi.StateReducer
import javax.inject.Inject

class ReleaseDrawerReducer @Inject constructor() : StateReducer<State, Action, ReleaseAction>() {

    override fun State.reduce(action: ReleaseAction): State {
        return IdleState
    }
}