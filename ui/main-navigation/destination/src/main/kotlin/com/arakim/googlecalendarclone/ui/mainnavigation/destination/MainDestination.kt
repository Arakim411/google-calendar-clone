package com.arakim.googlecalendarclone.ui.mainnavigation.destination

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
}