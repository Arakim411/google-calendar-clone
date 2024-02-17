package com.arakim.googlecalendarclone.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arakim.googlecalendarclone.ui.common.R.string
import com.arakim.googlecalendarclone.util.kotlin.CommonError

// TODO show info depending on common error
@Composable
fun CommonErrorView(
    commonError: CommonError? = null,
    onRetry: (() -> Unit)? = null
) {

    val style = MaterialTheme.typography.headlineLarge

    val message = when (commonError) {
        is CommonError.NoConnection -> string.common_error_no_connection
        else -> string.common_error_something_went_wrong
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Text(
                text = stringResource(message),
                style = style
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (onRetry != null) {
                Button(onClick = { onRetry() }) {
                    Text(text = stringResource(id = R.string.try_again_button_text))
                }
            }
        }
    }
}
