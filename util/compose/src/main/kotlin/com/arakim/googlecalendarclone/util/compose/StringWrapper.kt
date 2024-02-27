package com.arakim.googlecalendarclone.util.compose

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.arakim.googlecalendarclone.util.compose.StringWrapper.StringResources
import com.arakim.googlecalendarclone.util.compose.StringWrapper.StringValue

sealed interface StringWrapper {

    data class StringResources(
        @StringRes val resId: Int
    ) : StringWrapper

    data class StringValue(
        val value: String
    ) : StringWrapper
}

@Composable
@ReadOnlyComposable
inline fun StringWrapper.getString() = when (this) {
    is StringResources -> stringResource(id = resId)
    is StringValue -> value
}