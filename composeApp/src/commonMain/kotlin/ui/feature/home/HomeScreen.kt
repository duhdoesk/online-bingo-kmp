package ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import domain.feature.user.model.Tier
import domain.room.model.BingoType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_waterfall
import themedbingo.composeapp.generated.resources.home_play
import themedbingo.composeapp.generated.resources.home_vip_pass
import ui.feature.core.text.OutlinedShadowedText
import ui.feature.home.component.ClassicBingoCard
import ui.feature.home.component.HomeHeader
import ui.feature.home.component.ThemedBingoCard
import ui.feature.home.component.VipCard
import ui.navigation.Configuration
import ui.theme.AppTheme
import ui.theme.homeOnColor
import ui.theme.homeSecondaryColor

@Composable
fun HomeScreen(component: HomeScreenComponent) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onNavigate = component::navigate,
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onNavigate: (Configuration) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = {
            OutlinedShadowedText(
                text = "Hot Water Games",
                fontSize = 16,
                strokeWidth = 2f,
                fontColor = homeOnColor,
                strokeColor = homeSecondaryColor,
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth()
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = modifier
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(Res.drawable.bg_waterfall),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
                    .widthIn(max = 600.dp)
            ) {
                HomeHeader(
                    uiState = uiState,
                    onNavigateToMenu = { onNavigate(Configuration.ProfileScreen) }, // todo(): change to menu
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    OutlinedShadowedText(
                        text = stringResource(Res.string.home_play),
                        fontSize = 32,
                        strokeWidth = 2f,
                        fontColor = homeOnColor,
                        strokeColor = homeSecondaryColor,
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth()
                    )

                    ThemedBingoCard(
                        onClick = { onNavigate(Configuration.JoinScreen(BingoType.THEMED)) },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    ClassicBingoCard(
                        onClick = { onNavigate(Configuration.JoinScreen(BingoType.CLASSIC)) },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                    )

                    if (uiState.tier == Tier.FREE) {
                        OutlinedShadowedText(
                            text = stringResource(Res.string.home_vip_pass),
                            fontSize = 32,
                            strokeWidth = 2f,
                            fontColor = homeOnColor,
                            strokeColor = homeSecondaryColor,
                            modifier = Modifier
                                .padding(top = 40.dp, bottom = 12.dp)
                                .fillMaxWidth()
                        )

                        VipCard(
                            onClick = { onNavigate(Configuration.PaywallScreen) },
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        HomeScreen(
            uiState = HomeUiState(
                isLoading = false,
                username = "duhdoesk",
                message = "Themed Bingo is awesome! I like it so much!",
                pictureUrl = "",
                tier = Tier.FREE
            ),
            onNavigate = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
