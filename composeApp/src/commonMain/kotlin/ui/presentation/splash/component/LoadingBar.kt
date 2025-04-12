package ui.presentation.splash.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun LoadingBar(
    progress: Float,
    animationDuration: Duration = 1.seconds,
    borderRadius: Dp = 12.dp,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = animationDuration.inWholeMilliseconds.toInt()),
        label = "animatedProgress"
    )

    Box(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(borderRadius),
                clip = false
            )
    ) {
        Box(
            modifier = modifier
                .height(40.dp)
                .clip(RoundedCornerShape(borderRadius))
                .background(Color.LightGray)
                .border(4.dp, Color.White, RoundedCornerShape(borderRadius))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = animatedProgress)
                    .clip(RoundedCornerShape(borderRadius))
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFFFF5045),
                                Color(0xFFFCA053)
                            )
                        )
                    )
            )
        }
    }
}
