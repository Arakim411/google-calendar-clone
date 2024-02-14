package com.arakim.googlecalendarclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arakim.googlecalendarclone.domain.user.signin.SignInService
import com.arakim.googlecalendarclone.domain.user.signin.usecases.SignInUserUseCase
import com.arakim.googlecalendarclone.ui.mainnavigation.MainNavigation
import com.arakim.googlecalendarclone.ui.theme.GoogleCalendarCloneTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var signInUserUseCase: SignInUserUseCase

    @Inject
    lateinit var signInService: SignInService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GoogleCalendarCloneTheme {
                MainNavigation()
            }
        }
    }
}