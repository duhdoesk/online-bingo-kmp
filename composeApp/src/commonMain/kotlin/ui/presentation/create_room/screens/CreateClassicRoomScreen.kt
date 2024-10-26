package ui.presentation.create_room.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import themedbingo.composeapp.generated.resources.create_classic_room_title
import themedbingo.composeapp.generated.resources.name_textField
import ui.presentation.common.components.DoubleButtonRow
import ui.presentation.common.components.CreateRoomHeader
import ui.presentation.create_room.event.CreateScreenEvent
import ui.presentation.create_room.screens.components.CreateRoomEditMaxWinners
import ui.presentation.create_room.screens.components.CreateRoomEditPassword
import ui.presentation.create_room.screens.components.CreateRoomEditStringCard
import ui.presentation.create_room.state.CreateScreenUiState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CreateClassicRoomScreen(
    uiState: CreateScreenUiState,
    event: (event: CreateScreenEvent) -> Unit,
    onEditName: () -> Unit,
    onEditPassword: () -> Unit,
    isFormOk: Boolean,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .sizeIn(maxWidth = 600.dp, maxHeight = 1000.dp)
    ) {
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
                text = stringResource(Res.string.create_classic_room_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )

            CreateRoomEditStringCard(
                label = Res.string.name_textField,
                currentInfo = uiState.name,
                onClick = { onEditName() },
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            )

            Spacer(Modifier.height(12.dp))

            CreateRoomEditMaxWinners(
                currentValue = uiState.maxWinners,
                onClick = { maxWinners -> event(CreateScreenEvent.UpdateMaxWinners(maxWinners)) },
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            )

            Spacer(Modifier.height(12.dp))

            CreateRoomEditPassword(
                currentPassword = uiState.password,
                isLocked = uiState.locked,
                updateLockedState = { event(CreateScreenEvent.UpdateLocked) },
                updateCurrentPassword = { onEditPassword() },
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            )
        }

        DoubleButtonRow(
            leftClicked = { event(CreateScreenEvent.PopBack) },
            rightClicked = { event(CreateScreenEvent.CreateRoom) },
            rightText = Res.string.create_button,
            rightEnabled = isFormOk,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        )
    }
}