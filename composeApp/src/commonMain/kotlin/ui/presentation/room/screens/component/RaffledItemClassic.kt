@file:OptIn(ExperimentalResourceApi::class)

package ui.presentation.room.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.blue_ball
import themedbingo.composeapp.generated.resources.green_ball
import themedbingo.composeapp.generated.resources.purple_ball
import themedbingo.composeapp.generated.resources.red_ball
import themedbingo.composeapp.generated.resources.yellow_ball

@Composable
fun RaffledItemClassic(
    raffled: List<String>,
    isHost: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val item = raffled.lastOrNull()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            item?.let {
                val painter = when (item.toInt()) {
                    in 1..14 -> Res.drawable.blue_ball
                    in 15..29 -> Res.drawable.purple_ball
                    in 30..44 -> Res.drawable.yellow_ball
                    in 45..59 -> Res.drawable.green_ball
                    else -> Res.drawable.red_ball
                }

                Image(
                    painter = painterResource(painter),
                    contentDescription = null,
                    modifier = Modifier.size(140.dp),
                )

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .border(4.dp, Color.White, CircleShape),
                ) {
                    Text(
                        text = getFormattedNumber(item.toInt()),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(24.dp),
                        fontFamily = FontFamily.Monospace,
                    )
                }
            }
        }

        if (!isHost) return@Column

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .heightIn(max = 600.dp),
        ) {
            val numbers = (1..75).toList().map { it.toString() }

            items(numbers) { str ->
                val containerColor = GetContainerColor(str, raffled)

                Card(
                    modifier = Modifier.padding(2.dp).fillMaxSize(),
                ) {
                    Surface(color = containerColor) {
                        Text(
                            text = getFormattedNumber(str.toInt()),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Visible,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GetContainerColor(number: String, raffled: List<String>): Color {
    return if (number in raffled) MaterialTheme.colorScheme.primaryContainer
    else CardDefaults.cardColors().containerColor
}