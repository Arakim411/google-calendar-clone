package com.arakim.googlecalendarclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arakim.googlecalendarclone.domain.user.signin.SignInService
import com.arakim.googlecalendarclone.domain.user.signin.usecases.SignInUserUseCase
import com.arakim.googlecalendarclone.ui.mainnavigation.MainNavigation
import com.arakim.googlecalendarclone.ui.theme.GoogleCalendarCloneTheme
import com.arakim.googlecalendarclone.util.compose.windowsizeclass.WindowSizeType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // TODO handle with process death
    private val showNativeSplash = mutableStateOf(false)

    @Inject
    lateinit var signInUserUseCase: SignInUserUseCase

    @Inject
    lateinit var signInService: SignInService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { showNativeSplash.value }
        }
        setContent {
            GoogleCalendarCloneTheme {
                WindowSizeType {
                    MainNavigation(showNativeSplash = showNativeSplash)
                }
            }
        }
    }
}