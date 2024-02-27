package com.arakim.googlecalendarclone.ui.screen.signin.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arakim.googlecalendarclone.ui.screen.signin.R.drawable
import com.arakim.googlecalendarclone.ui.screen.signin.R.string

private const val SignInButtonCornerRadius = 50

@Composable
internal fun SignInWithGoogleButton(onClick: () -> Unit) {
    Image(
        modifier = Modifier.clickable { onClick() }.testTag(SignInScreenTestTags.SignInWithGoogleButton),
        painter = painterResource(id = drawable.sign_in_with_google_su),
        contentDescription = null
    )
}

@Composable
internal fun SignInWithFakeButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(175.dp)
            .height(40.dp)
            .testTag(SignInScreenTestTags.SignInWithFakeButton)
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(SignInButtonCornerRadius)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {

            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = drawable.ic_fake),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = string.sign_in_with_fake),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

object SignInScreenTestTags {
    const val SignInWithGoogleButton = "SignInWithGoogleButton"
    const val SignInWithFakeButton = "SignInWithFakeButton"
}