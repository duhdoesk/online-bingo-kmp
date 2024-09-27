package ui.presentation.home.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.hw_orange_bg

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BingoTypeCard(
    background: DrawableResource,
    icon: DrawableResource,
    title: StringResource,
    body: StringResource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(background),
                contentScale = ContentScale.Crop,
                contentDescription = "Background",
                modifier = Modifier.matchParentSize(),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(80.dp),
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )

                    Text(
                        text = stringResource(body),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        minLines = 3,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}