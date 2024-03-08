package com.arakim.googlecalendarclone.ui.screen.signin

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.google.android.gms.auth.UserRecoverableAuthException

class AuthExceptionHelper internal constructor(
    private val onFailToGainPermission: (Exception) -> Unit,
) {
    internal lateinit var requestGainPermission: (intent: Intent) -> Unit

    fun askForPermission(exception: UserRecoverableAuthException) {
        val intent = exception.intent ?: run {
            onFailToGainPermission(exception)
            return
        }
        requestGainPermission(intent)
    }
}

@Composable
fun rememberAuthExceptionHelper(
    onGainPermission: () -> Unit,
    onFailToGainPermission: (Exception) -> Unit,
): AuthExceptionHelper {

    val authExceptionHandler = remember {
        AuthExceptionHelper(onFailToGainPermission)
    }

    val onActivityResult = rememberLauncherForActivityResult(
        contract = StartActivityForResult(),
        onResult = {
            if (it.resultCode == Activity.RESULT_OK) {
                onGainPermission()
            } else {
                onFailToGainPermission(Exception("failed to get permission, result code: ${it.resultCode}"))
            }
        }
    )

    LaunchedEffect(Unit) {
        authExceptionHandler.requestGainPermission = {
            onActivityResult.launch(it)
        }
    }

    return authExceptionHandler
}
