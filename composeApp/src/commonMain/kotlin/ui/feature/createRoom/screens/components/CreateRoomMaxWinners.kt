package ui.feature.createRoom.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.baseline_add_24
import themedbingo.composeapp.generated.resources.baseline_remove_24
import ui.feature.createRoom.state.CreateScreenUiState
import ui.util.getRandomLightColor

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CreateRoomMaxWinners(
    modifier: Modifier = Modifier,
    uiState: CreateScreenUiState,
    leadingIconModifier: Modifier,
    onUpdateMaxWinners: (maxWinners: Int) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        val color by remember { mutableStateOf(getRandomLightColor()) }

        Box(modifier = leadingIconModifier) {
            Surface(
                color = color,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    tint = Color.Black,
                    contentDescription = "1, 2, 3",
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                )
            }
        }

        Spacer(Modifier.width(16.dp))

        Row(
            modifier = Modifier
                .padding(start = 6.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Max. Winners",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = { onUpdateMaxWinners(uiState.maxWinners - 1) },
                enabled = (uiState.maxWinners > 1)
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.baseline_remove_24),
                    contentDescription = "Decrease"
                )
            }

            Text(
                text = uiState.maxWinners.toString(),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            IconButton(
                onClick = { onUpdateMaxWinners(uiState.maxWinners + 1) },
                enabled = (uiState.maxWinners < 20)
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.baseline_add_24),
                    contentDescription = "Increase"
                )
            }
        }
    }
}
