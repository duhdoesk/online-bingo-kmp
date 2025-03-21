package ui.presentation.room.screens.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.room.model.BingoType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import ui.presentation.room.state.auxiliar.CardState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CompactSelectedBingoCardClassic(
    cardState: CardState.Success,
    raffled: List<String>,
    modifier: Modifier = Modifier
) {
    var index = 0
    val finalCard = mutableListOf<String>()

    finalCard.addAll(cardState.items)
    finalCard.add(12, "-")

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Text(
                    text = stringResource(BingoType.CLASSIC.stringResource).uppercase(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(5) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    repeat(5) {
                        CompactClassicCardIndividuals(
                            number = finalCard[index],
                            hasBeenRaffled = (finalCard[index] in raffled),
                            modifier = Modifier.fillMaxWidth()
                        )
                        index++
                    }
                }
            }
        }
    }
}
