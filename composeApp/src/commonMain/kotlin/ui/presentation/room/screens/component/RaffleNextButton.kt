package ui.presentation.room.screens.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.raffle_button
import ui.presentation.room.state.auxiliar.RaffleButtonState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RaffleNextButton(
    modifier: Modifier = Modifier,
    buttonState: RaffleButtonState,
    onClick: () -> Unit
) {
    val enabled = (buttonState == RaffleButtonState.AVAILABLE)

    ElevatedButton(
        onClick = { onClick() },
        enabled = enabled,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.height(32.dp)
        ) {
            when (buttonState) {
                RaffleButtonState.SUSPEND -> {
                    CircularProgressIndicator(Modifier.size(20.dp))
                }

                else -> {
                    Text(
                        text = stringResource(Res.string.raffle_button).uppercase(),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
