package ui.presentation.room.classic.play.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ExpandedClassicCardIndividuals(
    number: Int,
    modifier: Modifier = Modifier,
) {
    val background = when (number) {
        in 1..15 -> Color(0, 115, 230, 30)
        in 16..30 -> Color(115, 0, 230, 30)
        in 31..45 -> Color(230, 230, 0, 30)
        in 46..60 -> Color(0, 230, 0, 30)
        else -> Color(230, 0, 0, 30)
    }
    Card(modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(background),
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}