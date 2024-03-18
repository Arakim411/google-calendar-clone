package com.arakim.googlecalendarclone.ui.mainnavigation

import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arakim.googlecalendarclone.ui.calendar.presenter.CalendarAction.InitializationAction.InitializeAction
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination.HomeDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination.SignInDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination.SplashDestination
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.NavigationAction
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.NavigationAction.NavigateAction
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.NavigationAction.NavigateBackAction
import com.arakim.googlecalendarclone.ui.navigationdrawer.AppDrawerViewModel
import com.arakim.googlecalendarclone.ui.navigationdrawer.AppNavigationDrawerView
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerSideEffect.ItemClickedSideEffect.RefreshClickedSideEffect
import com.arakim.googlecalendarclone.ui.navigationdrawer.presenter.AppDrawerSideEffect.ItemClickedSideEffect.SignOutSideEffect
import com.arakim.googlecalendarclone.ui.screen.home.HomeScreen
import com.arakim.googlecalendarclone.ui.screen.home.HomeViewModel
import com.arakim.googlecalendarclone.ui.screen.signin.compose.SignInScreen
import com.arakim.googlecalendarclone.ui.screen.splash.SplashScreen
import kotlinx.coroutines.launch

// TODO intent filters for notifications itd..

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    showNativeSplash: MutableState<Boolean>,
) {

    val appDrawerViewModel = hiltViewModel<AppDrawerViewModel>()
    val drawerState = rememberDrawerState(initialValue = Closed)

    val coroutineScope = rememberCoroutineScope()

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

    fun changeIsDrawerOpen(isOpen: Boolean) {
        coroutineScope.launch {
            if (isOpen) {
                drawerState.open()
            } else {
                drawerState.close()
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
    AppNavigationDrawerView(
        viewModel = appDrawerViewModel,
        drawerState = drawerState,
    ) {
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
                val viewModel = hiltViewModel<HomeViewModel>()
                LaunchedEffect(Unit) {
                    appDrawerViewModel.appDrawerPresenter.sideEffectFlow.collect {
                        when (it) {
                            // TODO refresh instead reinitialize
                            RefreshClickedSideEffect -> {
                                viewModel.calendarPresenter.onAction(InitializeAction)
                                changeIsDrawerOpen(false)
                            }

                            SignOutSideEffect -> {
                                appDrawerViewModel.signOut()
                                changeIsDrawerOpen(false)
                                navigate(NavigateAction(SignInDestination, removeAllDestinations = true))
                            }

                            else -> {
                                Unit
                            }
                        }
                    }
                }
                HomeScreen(
                    viewModel = viewModel,
                    changeIsDrawerOpen = ::changeIsDrawerOpen,
                )
            }
        }
    }
}