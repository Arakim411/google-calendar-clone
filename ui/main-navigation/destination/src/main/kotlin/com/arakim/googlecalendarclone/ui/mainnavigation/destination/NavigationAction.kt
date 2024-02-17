package com.arakim.googlecalendarclone.ui.mainnavigation.destination

sealed interface NavigationAction {

    data class NavigateAction(
        val destination: MainDestination,
        val replaceCurrentDestination: Boolean = false,
        val removeAllDestinations: Boolean = false,
    ) : NavigationAction

    data object NavigateBackAction : NavigationAction
}