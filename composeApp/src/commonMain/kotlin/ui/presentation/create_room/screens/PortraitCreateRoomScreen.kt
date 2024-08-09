package ui.presentation.create_room.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.theme.model.BingoTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.create_button
import themedbingo.composeapp.generated.resources.create_room_title
import ui.presentation.common.components.BottomButtonRow
import ui.presentation.common.components.CreateRoomHeader
import ui.presentation.create_room.event.CreateScreenEvent
import ui.presentation.create_room.screens.components.CreateRoomMaxWinners
import ui.presentation.create_room.screens.components.CreateRoomName
import ui.presentation.create_room.screens.components.CreateRoomThemePicker
import ui.presentation.create_room.screens.components.SessionPasswordComponent
import ui.presentation.create_room.state.CreateScreenUiState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitCreateRoomScreen(
    themes: List<BingoTheme>,
    uiState: CreateScreenUiState,
    isFormOk: Boolean,
    event: (event: CreateScreenEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.imePadding(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .systemBarsPadding()
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {

                    val rowModifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()

                    val leadingIconModifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))

                    CreateRoomHeader()

                    Spacer(Modifier.height(60.dp))

                    Text(
                        text = stringResource(Res.string.create_room_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    CreateRoomName(
                        modifier = rowModifier,
                        uiState = uiState,
                        leadingIconModifier = leadingIconModifier,
                        onUpdateName = { name ->
                            event(CreateScreenEvent.UpdateName(name))
                        })

                    Spacer(Modifier.height(12.dp))

                    CreateRoomThemePicker(
                        modifier = rowModifier,
                        uiState = uiState,
                        themes = themes,
                        leadingIconModifier = leadingIconModifier,
                        contentScale = ContentScale.Crop,
                        onUpdateThemeId = { themeId ->
                            event(CreateScreenEvent.UpdateTheme(themeId))
                        }
                    )

                    Spacer(Modifier.height(12.dp))

                    CreateRoomMaxWinners(
                        modifier = rowModifier,
                        leadingIconModifier = leadingIconModifier,
                        uiState = uiState,
                        onUpdateMaxWinners = { maxWinners ->
                            event(CreateScreenEvent.UpdateMaxWinners(maxWinners))
                        }
                    )

                    Spacer(Modifier.height(12.dp))

                    SessionPasswordComponent(
                        modifier = rowModifier,
                        uiState = uiState,
                        leadingIconModifier = leadingIconModifier,
                        onUpdateLockedState = { event(CreateScreenEvent.UpdateLocked) },
                        onUpdatePassword = { password ->
                            event(
                                CreateScreenEvent.UpdatePassword(
                                    password
                                )
                            )
                        }
                    )
                }

                BottomButtonRow(
                    leftClicked = { event(CreateScreenEvent.PopBack) },
                    rightClicked = { event(CreateScreenEvent.CreateRoom) },
                    rightText = Res.string.create_button,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                )
            }

        }
    }
}