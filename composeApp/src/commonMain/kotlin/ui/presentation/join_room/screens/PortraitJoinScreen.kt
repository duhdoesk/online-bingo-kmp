package ui.presentation.join_room.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import domain.theme.model.BingoTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.create_button
import themedbingo.composeapp.generated.resources.join_room
import themedbingo.composeapp.generated.resources.search
import ui.presentation.common.components.BottomButtonRow
import ui.presentation.join_room.event.JoinRoomUIEvent
import ui.presentation.join_room.screens.component.JoinScreenLazyColumn
import ui.presentation.join_room.state.JoinRoomUIState

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun PortraitJoinScreen(
    uiState: JoinRoomUIState,
    uiEvent: (event: JoinRoomUIEvent) -> Unit,
    themes: List<BingoTheme>,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    Text(
                        text = stringResource(Res.string.join_room),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                    )

                    OutlinedTextField(
                        value = "",
                        onValueChange = { },
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                        label = { Text(stringResource(Res.string.search)) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(Res.string.search)
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                    )

                    Spacer(Modifier.height(16.dp))

                    if (uiState.notStartedRooms.isEmpty() && uiState.runningRooms.isEmpty()) {
                        //todo(): screen when there is no room to be shown
                    } else {
                        JoinScreenLazyColumn(
                            notStartedRooms = uiState.notStartedRooms,
                            runningRooms = uiState.runningRooms,
                            bingoThemes = themes,
                        )
                    }

                    if (uiState.loading) {
                        CircularProgressIndicator(modifier = Modifier.size(48.dp))
                    }
                }

                BottomButtonRow(
                    rightEnabled = true,
                    leftClicked = { uiEvent(JoinRoomUIEvent.PopBack) },
                    rightClicked = { uiEvent(JoinRoomUIEvent.CreateRoom) },
                    rightText = Res.string.create_button,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}