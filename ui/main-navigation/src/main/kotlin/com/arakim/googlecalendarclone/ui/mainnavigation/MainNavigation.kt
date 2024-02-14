package com.arakim.googlecalendarclone.ui.mainnavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination.HomeDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination.SignInDestination
import com.arakim.googlecalendarclone.ui.screen.home.HomeScreen
import com.arakim.googlecalendarclone.ui.screen.signin.compose.SignInScreen

// TODO intent filters for notifications itd..
// TODO common side/top bar controller state

@Composable
fun MainNavigation() {

    val navController = rememberNavController()

    @Stable
    fun navigate(destination: MainDestination) {
        navController.navigate(destination.navigateRoute)
    }

    NavHost(
        navController = navController,
        startDestination = SignInDestination.Route,
    ) {

        composable(SignInDestination.Route) {
            SignInScreen(navigate = ::navigate)
        }

        composable(HomeDestination.Route) {
            HomeScreen()
        }
    }
}