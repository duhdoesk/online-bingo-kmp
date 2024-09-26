package ui.presentation.join_room.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.no_rooms_body
import themedbingo.composeapp.generated.resources.no_rooms_title
import themedbingo.composeapp.generated.resources.sad_tiger

@OptIn(ExperimentalResourceApi::class)
@Composable
fun JoinScreenNoRoomsComponent(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(Res.drawable.sad_tiger),
            contentDescription = "Penguin",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp),
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.no_rooms_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = stringResource(Res.string.no_rooms_body),
            textAlign = TextAlign.Center,
        )
    }
}