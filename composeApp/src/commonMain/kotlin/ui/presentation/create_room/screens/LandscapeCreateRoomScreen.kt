package ui.presentation.create_room.screens

import androidx.compose.runtime.Composable
import domain.theme.model.BingoTheme
import ui.presentation.create_room.event.CreateScreenEvent

@Composable
fun LandscapeCreateRoomScreen(
    themes: List<BingoTheme>,
    event: (event: CreateScreenEvent) -> Unit
) {
}