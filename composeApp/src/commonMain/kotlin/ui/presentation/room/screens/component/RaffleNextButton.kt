package ui.presentation.room.screens.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    onClick: () -> Unit,
) {
    val enabled = (buttonState == RaffleButtonState.AVAILABLE)

    Button(
        onClick = { onClick() },
        enabled = enabled,
        modifier = modifier.animateContentSize(),
    ) {
        when (buttonState) {
            RaffleButtonState.SUSPEND -> {
                CircularProgressIndicator(Modifier.size(20.dp))
            }

            else -> {
                Text(
                    text = stringResource(Res.string.raffle_button),
                    modifier = Modifier.padding(horizontal = 28.dp),
                )
            }
        }
    }
}