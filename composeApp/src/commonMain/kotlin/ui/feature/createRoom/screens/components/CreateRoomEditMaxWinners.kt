package ui.feature.createRoom.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.baseline_add_24
import themedbingo.composeapp.generated.resources.baseline_remove_24
import themedbingo.composeapp.generated.resources.max_winners_card

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CreateRoomEditMaxWinners(
    currentValue: Int,
    onClick: (newValue: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        border = BorderStroke(2.dp, Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(Res.string.max_winners_card),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { onClick(currentValue - 1) },
                    enabled = (currentValue > 1)
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.baseline_remove_24),
                        contentDescription = "Decrease"
                    )
                }

                Text(
                    text = currentValue.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                IconButton(
                    onClick = { onClick(currentValue + 1) },
                    enabled = (currentValue < 20)
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.baseline_add_24),
                        contentDescription = "Increase"
                    )
                }
            }
        }
    }
}
