package ui.feature.createRoom.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.create_button
import themedbingo.composeapp.generated.resources.create_room_title
import themedbingo.composeapp.generated.resources.name_textField
import themedbingo.composeapp.generated.resources.theme_textField
import ui.feature.core.CreateRoomHeader
import ui.feature.core.PrimaryActionButton
import ui.feature.core.SingleButtonRow
import ui.feature.createRoom.event.CreateScreenEvent
import ui.feature.createRoom.screens.components.CreateRoomEditMaxWinners
import ui.feature.createRoom.screens.components.CreateRoomEditPassword
import ui.feature.createRoom.screens.components.CreateRoomEditStringCard
import ui.feature.createRoom.state.CreateScreenUiState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CreateThemedRoomScreen(
    uiState: CreateScreenUiState,
    isFormOk: Boolean,
    onEditName: () -> Unit,
    onEditPassword: () -> Unit,
    onEditTheme: () -> Unit,
    event: (event: CreateScreenEvent) -> Unit
) {
    val topPadding = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()

    Scaffold {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = topPadding)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.sizeIn(maxWidth = 600.dp, maxHeight = 1000.dp)) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
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

                    CreateRoomEditStringCard(
                        label = Res.string.name_textField,
                        currentInfo = uiState.name,
                        onClick = { onEditName() },
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    CreateRoomEditStringCard(
                        label = Res.string.theme_textField,
                        currentInfo = uiState.selectedTheme?.name.orEmpty(),
                        picture = uiState.selectedTheme?.pictureUri,
                        onClick = { onEditTheme() },
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    CreateRoomEditMaxWinners(
                        currentValue = uiState.maxWinners,
                        onClick = { maxWinners ->
                            event(
                                CreateScreenEvent.UpdateMaxWinners(
                                    maxWinners
                                )
                            )
                        },
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    CreateRoomEditPassword(
                        currentPassword = uiState.password,
                        isLocked = uiState.locked,
                        updateLockedState = { event(CreateScreenEvent.UpdateLocked) },
                        updateCurrentPassword = { onEditPassword() },
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                    )
                }

                PrimaryActionButton(
                    enabled = isFormOk,
                    text = stringResource(Res.string.create_button),
                    onClick = { event(CreateScreenEvent.CreateRoom) },
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )

                SingleButtonRow(
                    onClick = { event(CreateScreenEvent.PopBack) },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}
