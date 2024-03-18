package com.arakim.googlecalendarclone.ui.navigationdrawer.presenter

import androidx.compose.runtime.Immutable
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerAction.ReleaseAction
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreDrawerSideEffect.ItemInteractionSideEffect.ItemClickedSideEffect
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.CoreNavigationDrawerPresenter
import com.arakim.googlecaelndarclone.domain.user.User.SignedUser
import com.arakim.googlecalendarclone.domain.user.signin.usecases.UserFlowUseCase
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerAction.CoreStateChangedAction
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerAction.InitializationAction
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerAction.InitializationAction.InitializeAction
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerSideEffect.ItemClickedSideEffect.AccountClickedSideEffect
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerSideEffect.ItemClickedSideEffect.HelpFeedbackClickedSideEffect
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerSideEffect.ItemClickedSideEffect.RefreshClickedSideEffect
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerSideEffect.ItemClickedSideEffect.SettingsClickedSideEffect
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerSideEffect.ItemClickedSideEffect.SignOutSideEffect
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerState.IdleState
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.model.AppDrawerItemIds
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.reducer.CoreDrawerStateUpdatesReducer
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.reducer.InitializeReducer
import com.arakim.googlecalendarclone.util.kotlin.TypedResult
import com.arakim.googlecalendarclone.util.kotlin.onSuccess
import com.arakim.googlecalendarclone.util.mvi.ReducerPresenterWithSideEffect
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import kotlinx.coroutines.launch

typealias State = AppDrawerState
typealias Action = AppDrawerAction
typealias SideEffect = AppDrawerSideEffect

@Immutable
@ActivityRetainedScoped
class AppDrawerPresenter @Inject constructor(
    val corePresenter: CoreNavigationDrawerPresenter,
    val userFlow: UserFlowUseCase,
    initializeReducer: InitializeReducer,
    coreDrawerUpdatesReducer: CoreDrawerStateUpdatesReducer,
) : ReducerPresenterWithSideEffect<State, Action, SideEffect>(IdleState) {

    init {
        initializeReducer.coreDrawerPresenter = corePresenter
        coreDrawerUpdatesReducer.coreDrawerPresenter = corePresenter

        registerReducer<InitializationAction>(initializeReducer)
        registerReducer<CoreStateChangedAction>(coreDrawerUpdatesReducer)
    }

    override fun onInitialized() {
        super.onInitialized()
        corePresenter.initialize(coroutineScope)
        coroutineScope.launch {
            listenForUserChange()
        }
        coroutineScope.launch {
            listenAndMapSideEffects()
        }
    }

    private suspend fun listenForUserChange() {
        userFlow().collect { userResult ->
            userResult.onSuccess { user ->
                when (user) {
                    is SignedUser -> onAction(InitializeAction(user.name))
                    else -> corePresenter.onAction(ReleaseAction)
                }
            }
        }
    }

    private suspend fun listenAndMapSideEffects() {
        corePresenter.sideEffectFlow.collect { effect ->
            when (effect) {
                is ItemClickedSideEffect -> effect.toAppDrawerSideEffect().onSuccess { emitSideEffect(it) }
                else -> Unit
            }
        }
    }

    private fun ItemClickedSideEffect.toAppDrawerSideEffect(): TypedResult<AppDrawerSideEffect, Unit> {
        val sideEffect = when (this.item.id) {
            AppDrawerItemIds.RefreshId -> RefreshClickedSideEffect
            AppDrawerItemIds.AccountId -> AccountClickedSideEffect
            AppDrawerItemIds.SettingsId -> SettingsClickedSideEffect
            AppDrawerItemIds.HelpFeedbackId -> HelpFeedbackClickedSideEffect
            AppDrawerItemIds.SignOutId -> SignOutSideEffect
            else -> return TypedResult.failure(Unit)
        }

        return TypedResult.success(sideEffect)
    }
}