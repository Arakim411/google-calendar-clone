package com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.schedule.monthscolumn

import androidx.compose.animation.core.RepeatMode.Reverse
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arakim.googlecalendarclone.ui.calendar.compose.stateviews.schedule.DayRangeContainer

@Composable
fun ScheduleListSkeletonLoading() {

    val trans = rememberInfiniteTransition(label = "")
    val alpha = trans.animateFloat(
        initialValue = 0.1f, targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000),
            repeatMode = Reverse
        ),
        label = ""
    )

    Column(
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
            .alpha(alpha.value)
    ) {
        for (i in 0 until PlaceHoldersCount) {
            i
            Box(modifier = Modifier.height(60.dp)) {
                DayRangeContainer(
                    leadingView = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(25.dp)
                                    .background(Color.Gray, shape = CircleShape)
                            )
                        }
                    },
                    centerView = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(15.dp)
                                    .background(Color.Gray, MaterialTheme.shapes.small)
                            )
                        }
                    }
                )
            }
        }
    }
}

private const val PlaceHoldersCount = 10