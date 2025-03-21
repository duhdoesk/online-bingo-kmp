package ui.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ui.presentation.common.LoadingScreen
import ui.presentation.home.event.HomeScreenEvent
import ui.presentation.home.screens.PortraitHomeScreen
import ui.presentation.util.WindowInfo

@Composable
fun HomeScreen(
    component: HomeScreenComponent,
    windowInfo: WindowInfo
) {
    LaunchedEffect(Unit) { component.uiEvent(HomeScreenEvent.UILoaded) }

    val uiState by component.uiState.collectAsState()

    when (uiState.loading) {
        true ->
            LoadingScreen()

        false ->
            PortraitHomeScreen(uiState) { event -> component.uiEvent(event) }
    }
}
