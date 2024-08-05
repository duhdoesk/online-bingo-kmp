package ui.presentation.create_room

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import domain.util.api_response.ApiResponse
import kotlinx.coroutines.flow.stateIn
import ui.presentation.create_room.event.CreateScreenEvent
import ui.presentation.create_room.screens.CreateRoomScreenOrientation
import ui.presentation.create_room.screens.LandscapeCreateRoomScreen
import ui.presentation.create_room.screens.PortraitCreateRoomScreen
import ui.presentation.create_room.state.CreateScreenUiState
import ui.presentation.util.WindowInfo

@Composable
fun CreateRoomScreen(
    component: CreateRoomScreenComponent,
    windowInfo: WindowInfo
) {

    val themes by component
        .bingoThemesList
        .collectAsState()

    val uiState by component
        .uiState
        .collectAsState()

    val isFormOk by component.isFormOk.collectAsState()

    CreateRoomScreenOrientation(
        windowInfo = windowInfo,
        themes = themes,
        uiState = uiState,
        isFormOk = isFormOk,
        event = { event ->
            when (event) {
                CreateScreenEvent.CreateRoom ->
                    component.createRoom()

                CreateScreenEvent.PopBack ->
                    component.popBack()

                CreateScreenEvent.UpdateLocked ->
                    component.updateLocked()

                is CreateScreenEvent.UpdateName ->
                    component.updateName(event.name)

                is CreateScreenEvent.UpdateMaxWinners ->
                    component.updateMaxWinners(event.maxWinners)

                is CreateScreenEvent.UpdatePassword ->
                    component.updatePassword(event.password)

                is CreateScreenEvent.UpdateTheme ->
                    component.updateTheme(event.themeId)
            }
        }
    )
}