package ui.presentation.home

import androidx.compose.runtime.Composable
import ui.presentation.home.event.HomeScreenEvent
import ui.presentation.home.screens.LandscapeHomeScreen
import ui.presentation.home.screens.PortraitHomeScreen
import ui.presentation.util.WindowInfo

@Composable
fun HomeScreen(
    component: HomeScreenComponent,
    windowInfo: WindowInfo
) {
    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            LandscapeHomeScreen{ event ->
                when (event) {
                    is HomeScreenEvent.Navigate ->
                        component.navigate(event.configuration)
                }
            }

        else ->
            PortraitHomeScreen{ event ->
                when (event) {
                    is HomeScreenEvent.Navigate ->
                        component.navigate(event.configuration)
                }
            }
    }
}