package ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import domain.feature.user.model.Tier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_waterfall
import ui.feature.home.component.HomeHeader
import ui.navigation.Configuration
import ui.theme.AppTheme

@Composable
fun HomeScreen(
    component: HomeScreenComponent
) {
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
                tier = Tier.VIP
            ),
            onNavigate = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
