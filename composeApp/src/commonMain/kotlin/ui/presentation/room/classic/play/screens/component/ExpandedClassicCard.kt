package ui.presentation.room.classic.play.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.hot_water_logo
import ui.presentation.util.getRandomLightColor

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ExpandedClassicCard(
    card: List<Int>,
    modifier: Modifier = Modifier
) {
    var index = 0

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        repeat(2) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                repeat(5) {
                    ExpandedClassicCardIndividuals(
                        number = card[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                    index++
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            repeat(2) {
                ExpandedClassicCardIndividuals(
                    number = card[index],
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
                index++
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painter = painterResource(Res.drawable.hot_water_logo),
                            contentScale = ContentScale.Crop
                        ),
                )
            }

            repeat(2) {
                ExpandedClassicCardIndividuals(
                    number = card[index],
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
                index++
            }
        }

        repeat(2) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                repeat(5) {
                    ExpandedClassicCardIndividuals(
                        number = card[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                    index++
                }
            }
        }
    }
}