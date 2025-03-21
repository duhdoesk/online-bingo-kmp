package ui.presentation.room.screens.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CompactClassicCardIndividuals(
    number: String,
    hasBeenRaffled: Boolean,
    modifier: Modifier = Modifier
) {
    val containerColor =
        if (number == "-") {
            MaterialTheme.colorScheme.surface
        } else if (hasBeenRaffled) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        }

    val contentColor =
        if (number == "-") {
            MaterialTheme.colorScheme.onSurface
        } else if (hasBeenRaffled) {
            MaterialTheme.colorScheme.onSecondaryContainer
        } else {
            MaterialTheme.colorScheme.onSurface
        }

    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondaryContainer),
        colors = CardDefaults.cardColors().copy(
            contentColor = contentColor,
            containerColor = containerColor
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}
