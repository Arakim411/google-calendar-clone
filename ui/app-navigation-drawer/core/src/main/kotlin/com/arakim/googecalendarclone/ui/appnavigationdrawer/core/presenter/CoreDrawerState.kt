package com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter

import androidx.compose.runtime.Immutable
import com.arakim.googecalendarclone.ui.appnavigationdrawer.core.presenter.model.DrawerItemGroup
import com.arakim.googlecalendarclone.util.compose.ImmutableList

@Immutable
sealed interface CoreDrawerState {
    data object IdleState : CoreDrawerState
    data class ReadyState(val groups: ImmutableList<DrawerItemGroup>) : CoreDrawerState
}