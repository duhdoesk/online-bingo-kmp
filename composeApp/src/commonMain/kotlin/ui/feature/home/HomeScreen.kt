package ui.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ui.feature.core.LoadingScreen
import ui.feature.home.screens.PortraitHomeScreen
import ui.util.WindowInfo

@Composable
fun HomeScreen(
    component: HomeScreenComponent,
    windowInfo: WindowInfo
) {
    val uiState by component.uiState.collectAsState()

    when (uiState.loading) {
        true ->
            LoadingScreen()

        false ->
            PortraitHomeScreen(uiState) { config -> component.navigate(config) }
    }
}
