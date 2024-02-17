package com.arakim.googlecalendarclone.ui.mainnavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination.HomeDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination.SignInDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination.SplashDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.NavigationAction
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.NavigationAction.NavigateAction
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.NavigationAction.NavigateBackAction
import com.arakim.googlecalendarclone.ui.screen.home.HomeScreen
import com.arakim.googlecalendarclone.ui.screen.signin.compose.SignInScreen
import com.arakim.googlecalendarclone.ui.screen.splash.SplashScreen

// TODO intent filters for notifications itd..
// TODO common side/top bar controller state

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    showNativeSplash: MutableState<Boolean>,
) {

    @Stable
    fun navigate(action: NavigateAction) {
        if (action.replaceCurrentDestination) {
            navController.popBackStack()
        }

        navController.navigate(action.destination.navigateRoute) {
            if (action.removeAllDestinations) {
                popUpTo(0)
            }
        }
    }

    @Stable
    fun handleNavigationAction(action: NavigationAction) {
        when (action) {
            is NavigateAction -> navigate(action)
            NavigateBackAction -> navController.popBackStack()
        }
    }

    NavHost(
        navController = navController,
        startDestination = SplashDestination.Route,
    ) {
        composable(SplashDestination.Route) {
            SplashScreen(
                navigate = ::handleNavigationAction,
                showNativeSplash = showNativeSplash,
            )
        }

        composable(SignInDestination.Route) {
            SignInScreen(navigate = ::handleNavigationAction)
        }

        composable(HomeDestination.Route) {
            HomeScreen()
        }
    }
}