@file:OptIn(ExperimentalResourceApi::class)

package ui.feature.room.screens.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.feature.room.state.auxiliar.CardState

@Composable
fun CardSelectorClassic(
    cardState: CardState.Success,
    modifier: Modifier = Modifier
) {
    var index = 0

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Card(modifier = Modifier.widthIn(400.dp)) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
//                Text(
//                    text = stringResource(Res.string.classic_bingo).uppercase(),
//                    textAlign = TextAlign.Center,
//                    style = MaterialTheme.typography.headlineSmall,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier
//                        .padding(4.dp)
//                        .fillMaxWidth()
//                )
            }
        }

        Spacer(Modifier.height(8.dp))

        ExpandedClassicCard(
            card = cardState.items.map { it.toInt() },
            modifier = Modifier
        )
    }
}
