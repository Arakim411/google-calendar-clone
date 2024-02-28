package com.arakim.googlecalendarclone.ui.navigationdrawer.presenter

sealed interface AppDrawerState {
    data object IdleState : AppDrawerState
}