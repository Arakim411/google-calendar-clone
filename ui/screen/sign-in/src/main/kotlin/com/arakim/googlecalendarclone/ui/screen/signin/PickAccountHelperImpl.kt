package com.arakim.googlecalendarclone.ui.screen.signin

import android.Manifest
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.common.AccountPicker

// TODO could be extracted to separate module
internal class PickAccountHelperImpl internal constructor(
    private val context: Context,
) : PickAccountHelper {

    lateinit var requestAccountPermission: () -> Unit
    lateinit var chooseAccount: (Intent) -> Unit

    override fun pickAccount() {
        if (context.checkSelfPermission(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            chooseAccount(getPickAccountIntent())
        } else {
            requestAccountPermission()
        }
    }

    fun onAccountPermissionGranted() {
        pickAccount()
    }

    private fun getPickAccountIntent(): Intent = AccountPicker.newChooseAccountIntent(
        null, null, arrayOf("com.google", "com.google.android.legacyimap"),
        false, null, null, null, null
    )
}

@Composable
fun rememberPickAccountHelper(
    onAccountPicked: (account: String) -> Unit
): PickAccountHelper {
    val context = LocalContext.current
    val pickAccountHelper = remember { PickAccountHelperImpl(context) }

    val grantAccountPermissionLauncher = rememberLauncherForActivityResult(
        contract = RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                pickAccountHelper.onAccountPermissionGranted()
            }
        },
    )

    val pickAccountLauncher = rememberLauncherForActivityResult(
        contract = StartActivityForResult(),
        onResult = {
            val accountName = it.data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
            if (accountName != null) {
                onAccountPicked(accountName)
            }
        },
    )

    LaunchedEffect(Unit) {
        pickAccountHelper.requestAccountPermission = {
            grantAccountPermissionLauncher.launch(Manifest.permission.GET_ACCOUNTS)
        }
        pickAccountHelper.chooseAccount = {
            pickAccountLauncher.launch(it)
        }
    }

    return pickAccountHelper
}