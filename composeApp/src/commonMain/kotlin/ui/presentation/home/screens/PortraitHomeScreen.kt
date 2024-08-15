package ui.presentation.home.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bingo_balls
import themedbingo.composeapp.generated.resources.classic_bingo
import themedbingo.composeapp.generated.resources.classic_bingo_desc
import themedbingo.composeapp.generated.resources.home_screen
import themedbingo.composeapp.generated.resources.hw_blue_bg
import themedbingo.composeapp.generated.resources.hw_green_bg
import themedbingo.composeapp.generated.resources.player_picture
import themedbingo.composeapp.generated.resources.smiling_squirrel
import themedbingo.composeapp.generated.resources.themed_bingo
import themedbingo.composeapp.generated.resources.themed_bingo_desc
import ui.navigation.Configuration
import ui.presentation.common.components.NoPictureHWBanner
import ui.presentation.home.event.HomeScreenEvent
import ui.presentation.home.screens.component.BingoTypeCard
import ui.presentation.home.screens.component.HomeScreenHello
import ui.presentation.home.state.HomeScreenUIState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitHomeScreen(
    uiState: HomeScreenUIState,
    event: (event: HomeScreenEvent) -> Unit,
) {
    Scaffold(modifier = Modifier.imePadding()) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .systemBarsPadding()
                .fillMaxSize(),
        ) {
            Column(modifier = Modifier.sizeIn(maxWidth = 600.dp, maxHeight = 1000.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { event(HomeScreenEvent.Navigate(Configuration.ProfileScreen)) },
                ) {
                    Text(
                        text = stringResource(Res.string.home_screen),
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(1f),
                    )

                    AsyncImage(
                        model = uiState.userPicture,
                        contentDescription = stringResource(Res.string.player_picture),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(60.dp)
                            .clip(CircleShape),
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                ) {
                    HomeScreenHello(
                        userName = uiState.userName,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    BingoTypeCard(
                        background = Res.drawable.hw_green_bg,
                        icon = Res.drawable.smiling_squirrel,
                        title = Res.string.themed_bingo,
                        body = Res.string.themed_bingo_desc,
                        onClick = { event(HomeScreenEvent.Navigate(Configuration.JoinScreen)) },
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    Spacer(Modifier.height(8.dp))

                    BingoTypeCard(
                        background = Res.drawable.hw_blue_bg,
                        icon = Res.drawable.bingo_balls,
                        title = Res.string.classic_bingo,
                        body = Res.string.classic_bingo_desc,
                        onClick = { event(HomeScreenEvent.Navigate(Configuration.JoinScreen)) }, //todo(): refactor
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }

                NoPictureHWBanner(modifier = Modifier.padding(16.dp).fillMaxWidth())
            }
        }
    }
}