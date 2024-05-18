package ui.presentation.create_room.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.baseline_123_24
import themedbingo.composeapp.generated.resources.baseline_abc_24
import themedbingo.composeapp.generated.resources.baseline_add_24
import themedbingo.composeapp.generated.resources.baseline_add_circle_24
import themedbingo.composeapp.generated.resources.baseline_add_circle_outline_24
import themedbingo.composeapp.generated.resources.baseline_remove_24
import themedbingo.composeapp.generated.resources.baseline_remove_circle_24
import themedbingo.composeapp.generated.resources.baseline_remove_circle_outline_24
import ui.presentation.create_room.event.CreateScreenEvent
import ui.presentation.create_room.state.CreateScreenUiState
import ui.presentation.util.getRandomLightColor

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
                    imageVector = vectorResource(resource = Res.drawable.baseline_123_24),
                    tint = Color.Black,
                    contentDescription = "1, 2, 3",
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(start = 6.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
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