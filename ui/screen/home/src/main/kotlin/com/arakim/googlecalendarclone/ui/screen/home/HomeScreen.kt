package com.arakim.googlecalendarclone.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.arakim.googlecalendarclone.util.compose.windowsizeclass.ImpactProperty
import com.arakim.googlecalendarclone.util.compose.windowsizeclass.WindowSizeDelimiter
import com.arakim.googlecalendarclone.util.compose.windowsizeclass.WindowSizeType.Compact
import com.arakim.googlecalendarclone.util.compose.windowsizeclass.WindowSizeType.Expanded
import com.arakim.googlecalendarclone.util.compose.windowsizeclass.WindowSizeType.Medium

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()

    Column {

        WindowSizeDelimiter(impactedProperty = ImpactProperty.Height) {
            when (it) {
                Compact -> Text(text = "compact")
                Medium -> Text(text = "medium")
                Expanded -> Text(text = "expanded")
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = { viewModel.signOut() }) {
                Text(text = "sign out")
            }
            Text(text = "Home screen", style = MaterialTheme.typography.displayLarge)
        }
    }
}