package com.arakim.googlecalendarclone.ui.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arakim.googlecalendarclone.ui.mainnavigation.destination.MainDestination

@Composable
fun SplashScreen(
    navigate: (MainDestination) -> Unit
) {
    // TODO implement
    LaunchedEffect(Unit) {
        navigate(MainDestination.SignInDestination)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Splash Screen", style = MaterialTheme.typography.displayLarge)
    }
}