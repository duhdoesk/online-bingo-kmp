package ui.presentation.room.classic.host.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.blue_ball
import themedbingo.composeapp.generated.resources.green_ball
import themedbingo.composeapp.generated.resources.purple_ball
import themedbingo.composeapp.generated.resources.raffled
import themedbingo.composeapp.generated.resources.red_ball
import themedbingo.composeapp.generated.resources.yellow_ball
import ui.presentation.room.classic.component.RaffledNumber
import ui.presentation.room.classic.component.RaffledNumbersHorizontalPager
import ui.presentation.room.classic.component.getFormattedNumber
import ui.presentation.room.classic.host.NUMBERS

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ClassicRunningRoomScreenComposable(
    raffledNumbers: List<Int>,
    totalNumbers: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Text(
            text = "${stringResource(Res.string.raffled)}: ${raffledNumbers.size} / $totalNumbers",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        )

        if (raffledNumbers.isEmpty()) return@Column

        Spacer(Modifier.height(16.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier,
        ) {
            val painter = when (raffledNumbers.last()) {
                in 1..14 -> Res.drawable.blue_ball
                in 15..29 -> Res.drawable.purple_ball
                in 30..44 -> Res.drawable.yellow_ball
                in 45..59 -> Res.drawable.green_ball
                else -> Res.drawable.red_ball
            }

            Image(
                painter = painterResource(painter),
                contentDescription = null,
                modifier = Modifier.size(200.dp),
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .border(4.dp, Color.White, CircleShape),
            ) {
                Text(
                    text = getFormattedNumber(raffledNumbers.last()),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(24.dp)
                )
            }
        }
    }
}