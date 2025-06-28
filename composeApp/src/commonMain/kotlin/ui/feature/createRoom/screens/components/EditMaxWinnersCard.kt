package ui.feature.createRoom.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.room.model.BingoType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.ic_decrease
import themedbingo.composeapp.generated.resources.ic_increase
import themedbingo.composeapp.generated.resources.max_winners
import ui.feature.core.text.OutlinedText
import ui.theme.CreateRoomTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EditMaxWinnersCard(
    currentValue: Int,
    onClick: (newValue: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth()
        ) {
            OutlinedText(
                text = stringResource(Res.string.max_winners),
                fontSize = 20,
                strokeWidth = 0f,
                fontColor = MaterialTheme.colorScheme.primary,
                strokeColor = MaterialTheme.colorScheme.primary,
                maxLines = 2,
                modifier = Modifier.padding(end = 8.dp)
            )

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = { onClick(currentValue - 1) },
                enabled = currentValue > 1
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_decrease),
                    contentDescription = "Decrease"
                )
            }

            OutlinedText(
                text = currentValue.toString(),
                fontSize = 32,
                strokeWidth = 0f,
                fontColor = MaterialTheme.colorScheme.primary,
                strokeColor = MaterialTheme.colorScheme.primary
            )

            IconButton(
                onClick = { onClick(currentValue + 1) },
                enabled = currentValue < 20
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_increase),
                    contentDescription = "Increase"
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CreateRoomTheme(
        type = BingoType.THEMED,
        content = {
            Surface {
                EditMaxWinnersCard(
                    currentValue = 1,
                    onClick = {},
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    )
}
