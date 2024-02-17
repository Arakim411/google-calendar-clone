package com.arakim.googlecalendarclone.ui.mainnavigation.destination

import com.arakim.googlecalendarclone.ui.mainnavigation.destination.NavigationAction.NavigateAction

sealed interface MainDestination {

    val navigateRoute: String

    data object SignInDestination : MainDestination {
        const val Route = "sign_in"
        override val navigateRoute: String = Route
    }

    data object HomeDestination : MainDestination {
        const val Route = "home"
        override val navigateRoute: String = Route
    }

    data object SplashDestination : MainDestination {
        const val Route = "splash"
        override val navigateRoute: String = Route
    }
}

fun MainDestination.toAction(
    replaceCurrentDestination: Boolean = false,
    removeAllDestinations: Boolean = false,
): NavigationAction = NavigateAction(
    destination = this,
    replaceCurrentDestination = replaceCurrentDestination,
    removeAllDestinations = removeAllDestinations,
)