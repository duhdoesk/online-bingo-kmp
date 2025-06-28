package ui.feature.lobby

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import domain.feature.user.model.Tier
import domain.room.model.BingoRoom
import domain.room.model.BingoType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_beach_volley
import themedbingo.composeapp.generated.resources.bg_forest_sunlight
import themedbingo.composeapp.generated.resources.create_button
import themedbingo.composeapp.generated.resources.lobby
import ui.feature.core.CustomTopBar
import ui.feature.core.buttons.CustomPrimaryButton
import ui.feature.lobby.component.JoinBottomSheet
import ui.feature.lobby.component.RoomsLazyColumn
import ui.feature.lobby.component.SearchTextField
import ui.theme.CreateRoomTheme
import ui.util.collectInLaunchedEffect

@Composable
fun LobbyScreen(viewModel: LobbyScreenComponent) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    viewModel.uiEffect.collectInLaunchedEffect { effect ->
        when (effect) {
            is LobbyScreenUiEffect.ShowSnackbar -> {
                snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    CreateRoomTheme(
        type = viewModel.bingoType,
        content = {
            LobbyScreenContent(
                uiState = uiState,
                bingoType = viewModel.bingoType,
                snackbarHostState = snackbarHostState,
                onUiEvent = viewModel::uiEvent,
                modifier = Modifier.fillMaxSize()
            )
        }
    )
}

@Composable
private fun LobbyScreenContent(
    uiState: LobbyScreenUiState,
    bingoType: BingoType = BingoType.CLASSIC,
    snackbarHostState: SnackbarHostState,
    onUiEvent: (LobbyScreenUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val selectedRoom = remember { mutableStateOf<BingoRoom?>(null) }

    Scaffold(
        topBar = {
            CustomTopBar(
                text = stringResource(Res.string.lobby),
                primaryColor = MaterialTheme.colorScheme.primary,
                onPrimaryColor = MaterialTheme.colorScheme.onPrimary,
                onBackPressed = { onUiEvent(LobbyScreenUiEvent.OnPopBack) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = modifier
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { focusManager.clearFocus(); keyboardController?.hide() }
                    )
                }
        ) {
            val painter = when (bingoType) {
                BingoType.THEMED -> Res.drawable.bg_forest_sunlight
                BingoType.CLASSIC -> Res.drawable.bg_beach_volley
            }

            Image(
                painter = painterResource(painter),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
                    .widthIn(max = 600.dp)
            ) {
                Spacer(Modifier.height(20.dp))

                SearchTextField(
                    currentValue = uiState.query,
                    onValueChange = { onUiEvent(LobbyScreenUiEvent.OnQueryChange(it)) },
                    onDone = { keyboardController?.hide(); focusManager.clearFocus() },
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .height(56.dp)
                        .fillMaxWidth()
                )

                if (uiState.isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    RoomsLazyColumn(
                        rooms = uiState.availableRooms,
                        onRoomClicked = { selectedRoom.value = it },
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .weight(1f)
                    )
                }

                CustomPrimaryButton(
                    text = stringResource(Res.string.create_button),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = {
                        onUiEvent(LobbyScreenUiEvent.OnCreateRoom)
                        return@CustomPrimaryButton
                        if (uiState.user?.tier == Tier.VIP) {
                            onUiEvent(LobbyScreenUiEvent.OnCreateRoom)
                        } else {
                            onUiEvent(LobbyScreenUiEvent.OnNavigateToPaywall)
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .fillMaxWidth()
                )
            }
        }

        selectedRoom.value?.let { room ->
            JoinBottomSheet(
                room = room,
                onDismiss = { selectedRoom.value = null },
                onConfirm = { password -> onUiEvent(LobbyScreenUiEvent.OnJoinRoom(room, password)) }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    LobbyScreenContent(
        uiState = mockLobbyScreenUiState(),
        snackbarHostState = remember { SnackbarHostState() },
        onUiEvent = {}
    )
}
