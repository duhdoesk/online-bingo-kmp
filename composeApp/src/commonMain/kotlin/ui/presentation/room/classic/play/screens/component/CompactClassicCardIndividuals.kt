package ui.presentation.room.classic.play.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
        if (number == "-") MaterialTheme.colorScheme.surface
        else if (hasBeenRaffled) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.primaryContainer

    val contentColor =
        if (number == "-") MaterialTheme.colorScheme.surface
        else if (hasBeenRaffled) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onPrimaryContainer

    Card(modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(containerColor),
        ) {
            Text(
                text = number,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = contentColor,
                modifier = Modifier.padding(vertical = 4.dp),
            )
        }
    }
}