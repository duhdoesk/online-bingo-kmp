package ui.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.feature.user.model.Tier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_pool_water
import themedbingo.composeapp.generated.resources.bg_purple_gradient
import themedbingo.composeapp.generated.resources.hot_water_logo
import themedbingo.composeapp.generated.resources.ic_menu
import ui.feature.core.buttons.CustomIconButton
import ui.feature.core.text.OutlinedText
import ui.feature.home.HomeUiState
import ui.theme.AppTheme
import ui.theme.homeOnColor
import ui.theme.homePrimaryColor

@Composable
fun HomeHeader(
    uiState: HomeUiState,
    onNavigateToMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        UserPicture(
            pictureUrl = uiState.pictureUrl,
            modifier = Modifier
                .padding(start = 20.dp)
                .padding(vertical = 8.dp)
                .size(80.dp)
        )

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            UserInfo(
                name = uiState.username,
                message = uiState.message,
                modifier = Modifier
                    .padding(end = 48.dp)
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(80.dp)
            )

            MenuButton(
                onClick = onNavigateToMenu,
                modifier = Modifier
                    .padding(end = 20.dp, bottom = 8.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
private fun UserPicture(
    pictureUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .border(4.dp, homeOnColor, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        AsyncImage(
            model = pictureUrl,
            placeholder = painterResource(Res.drawable.hot_water_logo),
            error = painterResource(Res.drawable.hot_water_logo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun UserInfo(
    name: String,
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(3.dp, RoundedCornerShape(12.dp))
            .border(4.dp, homeOnColor, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        Image(
            painter = painterResource(Res.drawable.bg_pool_water),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.matchParentSize()
        ) {
            OutlinedText(
                text = name,
                fontSize = 24,
                strokeWidth = 3f,
                fontColor = homeOnColor,
                strokeColor = homePrimaryColor,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 16.dp, end = 44.dp)
            )

            OutlinedText(
                text = message,
                fontSize = 12,
                strokeWidth = 1f,
                fontColor = homePrimaryColor,
                strokeColor = homeOnColor,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 16.dp, end = 44.dp),
                maxLines = 2
            )
        }
    }
}

@Composable
private fun MenuButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CustomIconButton(
        icon = painterResource(Res.drawable.ic_menu),
        background = painterResource(Res.drawable.bg_purple_gradient),
        contentColor = homeOnColor,
        onClick = onClick,
        buttonSize = 48.dp,
        modifier = modifier
    )
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Surface {
            HomeHeader(
                uiState = HomeUiState(
                    isLoading = false,
                    username = "duhdoesk",
                    message = "Themed Bingo is awesome! I like it so much!",
                    pictureUrl = "",
                    tier = Tier.VIP
                ),
                onNavigateToMenu = {}
            )
        }
    }
}
