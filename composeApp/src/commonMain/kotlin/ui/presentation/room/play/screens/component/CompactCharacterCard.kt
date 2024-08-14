package ui.presentation.room.play.screens.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.character.model.Character

@Composable
fun CompactCharacterCard(
    character: Character,
    hasBeenRaffled: Boolean,
    modifier: Modifier = Modifier,
) {
    val containerColor =
        if (hasBeenRaffled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer

    val contentColor =
        if (hasBeenRaffled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer

    Card(modifier = modifier) {
        Surface(
            color = containerColor,
            contentColor = contentColor
        ) {
            Text(
                text = character.name.uppercase(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 6.dp)
                    .fillMaxWidth(),
            )
        }
    }
}