package ui.presentation.join_room.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import themedbingo.composeapp.generated.resources.subscription_dialog_body
import themedbingo.composeapp.generated.resources.subscription_dialog_title
import ui.navigation.Configuration
import ui.presentation.common.components.DoubleButtonRow
import ui.presentation.join_room.event.JoinRoomUIEvent
import ui.presentation.join_room.screens.component.JoinScreenLazyColumn
import ui.presentation.join_room.screens.component.JoinScreenNoRoomsComponent
import ui.presentation.join_room.state.JoinRoomUIState
import ui.presentation.util.dialog.GenericActionDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PortraitJoinScreen(
    uiState: JoinRoomUIState,
    uiEvent: (event: JoinRoomUIEvent) -> Unit,
    themes: List<BingoTheme>,
) {
    /**
     * Keyboard controller
     */
    val keyboardController = LocalSoftwareKeyboardController.current

    /**
     * Subscription Dialog
     */
    var showSubscriptionDialog by remember { mutableStateOf(false) }

    val topPadding = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()

    Scaffold {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = topPadding)
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
                        value = uiState.query,
                        onValueChange = { uiEvent(JoinRoomUIEvent.QueryTyping(it)) },
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

                    if (uiState.loading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(48.dp))
                        }
                    } else if (uiState.notStartedRooms.isEmpty() && uiState.runningRooms.isEmpty()) {
                        JoinScreenNoRoomsComponent(
                            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                                .weight(1f)
                        )
                    } else {
                        JoinScreenLazyColumn(
                            notStartedRooms = uiState.notStartedRooms,
                            runningRooms = uiState.runningRooms,
                            bingoThemes = themes,
                            onTapRoom = { uiEvent(it) }
                        )
                    }
                }

                DoubleButtonRow(
                    rightEnabled = true,
                    leftClicked = { uiEvent(JoinRoomUIEvent.PopBack) },
                    rightClicked = {
                        if (uiState.isSubscribed) uiEvent(JoinRoomUIEvent.CreateRoom)
                        else showSubscriptionDialog = true
                    },
                    rightText = Res.string.create_button,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .fillMaxWidth()
                )
            }

            /**
             * Dialog that informs user that subscription is needed to proceed
             */
            if (showSubscriptionDialog) {
                GenericActionDialog(
                    onDismiss = { showSubscriptionDialog = false },
                    onConfirm = {
                        showSubscriptionDialog = false
                        uiEvent(JoinRoomUIEvent.Navigate(Configuration.PaywallScreen))
                    },
                    title = Res.string.subscription_dialog_title,
                    body = Res.string.subscription_dialog_body,
                    permanentAction = false,
                )
            }
        }
    }
}