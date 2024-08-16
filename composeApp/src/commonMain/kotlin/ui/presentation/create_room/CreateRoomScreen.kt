package ui.presentation.create_room

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import domain.room.model.BingoType
import ui.presentation.common.RotateScreen
import ui.presentation.create_room.event.CreateScreenEvent
import ui.presentation.create_room.screens.CreateClassicRoomScreen
import ui.presentation.create_room.screens.CreateThemedRoomScreen
import ui.presentation.util.WindowInfo

@Composable
fun CreateRoomScreen(
    component: CreateRoomScreenComponent,
    windowInfo: WindowInfo
) {
    LaunchedEffect(Unit) { component.uiEvent(CreateScreenEvent.UILoaded) }

    val uiState by component.uiState.collectAsState()
    val isFormOk by component.isFormOk.collectAsState()

    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            RotateScreen()

        WindowInfo.DeviceOrientation.Portrait ->
            when (uiState.bingoType) {
                BingoType.CLASSIC ->
                    CreateClassicRoomScreen(
                        uiState = uiState,
                        isFormOk = isFormOk,
                        event = { component.uiEvent(it) }
                    )

                BingoType.THEMED ->
                    CreateThemedRoomScreen(
                        uiState = uiState,
                        isFormOk = isFormOk,
                        event = { component.uiEvent(it) }
                    )
            }
    }
}
