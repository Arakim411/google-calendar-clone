package com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.reducer

import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerAction.SetGroupsAction
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerState
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreNavigationDrawerPresenter
import com.arakim.googlecalendarclone.domain.calendarsetup.usecases.GetCalendarSetUpUseCase
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.Action
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerAction
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerAction.InitializationAction
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerAction.InitializationAction.InitializeAction
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerAction.InitializationAction.InitializeFailedFailedAction
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerAction.InitializationAction.SignOutAction
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerSideEffect
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerSideEffect.ItemClickedSideEffect.SignOutSideEffect
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerState.ErrorState
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerState.InitializingState
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.State
import com.arakim.googlecalendarclone.util.kotlin.getOrThrow
import com.arakim.googlecalendarclone.util.kotlin.yielded
import com.arakim.googlecalendarclone.util.mvi.StateReducerWithSideEffect
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withTimeout

class InitializeReducer @Inject constructor(
    private val getCalendarSetUp: GetCalendarSetUpUseCase,
) : StateReducerWithSideEffect<State, Action, InitializationAction, AppDrawerSideEffect>() {

    lateinit var coreDrawerPresenter: CoreNavigationDrawerPresenter
    private var coreStateUpdateJob: Job? = null

    override fun State.reduce(action: InitializationAction): State = when (action) {
        is InitializeAction -> {
            reduceInitializeAction(action)
        }

        InitializeFailedFailedAction -> {
            ErrorState
        }

        SignOutAction -> {
            emitSideEffect(SignOutSideEffect)
            this
        }
    }

    private fun State.reduceInitializeAction(action: InitializeAction): State {
        coroutineScope.yielded {
            initialize(action.userName)
        }
        return InitializingState
    }

    private suspend fun initialize(userName: String) {
        try {
            val setUp = getCalendarSetUp().getOrThrow()
            coreDrawerPresenter.onAction(SetGroupsAction(getDrawerItemsGroups(userName, setUp)))
            withTimeout(initializationTimeOut) { waitForCoreDrawerReadyState() }
            listenForCoreDrawerStatesUpdates()
        } catch (t: TimeoutCancellationException) {
            onAction(InitializeFailedFailedAction)
        }
    }

    private suspend fun waitForCoreDrawerReadyState() =
        coreDrawerPresenter.stateFlow.filter { it is CoreDrawerState.ReadyState }.first()

    private fun listenForCoreDrawerStatesUpdates() {
        coreStateUpdateJob?.cancel()
        coreStateUpdateJob = coreDrawerPresenter.stateFlow.onEach {
            onAction(AppDrawerAction.CoreStateChangedAction(it))
        }.launchIn(coroutineScope)
    }

    companion object {
        private val initializationTimeOut = 10.seconds
    }
}
