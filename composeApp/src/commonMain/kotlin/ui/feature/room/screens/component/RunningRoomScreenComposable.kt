package ui.feature.room.screens.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.character.model.Character
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.raffled

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RunningRoomScreenComposable(
    raffled: List<String>,
    characters: List<Character>,
    modifier: Modifier = Modifier
) {
    val raffledCharacters = raffled.map { id ->
        characters.find { character -> character.id == id }
    }.filterNotNull()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = "${stringResource(Res.string.raffled)}: ${raffled.size} / ${characters.size}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
        )

        RaffledCharactersHorizontalPager(
            characters = raffledCharacters,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
