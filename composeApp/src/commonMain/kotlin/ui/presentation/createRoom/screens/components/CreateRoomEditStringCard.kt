package ui.presentation.createRoom.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ui.presentation.util.getRandomLightColor

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CreateRoomEditStringCard(
    label: StringResource,
    currentInfo: String,
    picture: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color by remember { mutableStateOf(getRandomLightColor()) }

    Row(modifier = modifier.height(IntrinsicSize.Max)) {
        if (picture != null) {
            Box(
                modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(12.dp)) // Adiciona a sombra
                    .clip(RoundedCornerShape(12.dp)) // Mantém a borda arredondada
                    .border(2.dp, Color.White, RoundedCornerShape(12.dp))
            ) {
                AsyncImage(
                    model = picture,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )
            }

            Spacer(Modifier.width(8.dp))
        }

        Card(
            modifier = Modifier.weight(1f),
            onClick = { onClick() },
            border = BorderStroke(2.dp, Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(label),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = currentInfo,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Action Icon"
                    )
                }
            }
        }
    }
}
