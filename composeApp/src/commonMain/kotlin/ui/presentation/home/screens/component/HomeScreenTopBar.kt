package ui.presentation.home.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.home_screen
import themedbingo.composeapp.generated.resources.hw_orange_bg
import themedbingo.composeapp.generated.resources.vip

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreenTopBar(
    isSubscribed: Boolean,
    modifier: Modifier = Modifier,
    onClickSettings: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier,
    ) {
        if (isSubscribed) {
            Card(
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.padding(start = 16.dp),
            ) {
                Box {
                    Image(
                        painter = painterResource(Res.drawable.hw_orange_bg),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Background",
                        modifier = Modifier.matchParentSize(),
                    )

                    Text(
                        text = stringResource(Res.string.vip),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                    )
                }
            }
        }

        Text(
            text = stringResource(Res.string.home_screen),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f),
        )

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Account Settings",
            modifier = Modifier
                .padding(end = 16.dp)
                .size(30.dp)
                .clickable { onClickSettings() },
        )
    }
}