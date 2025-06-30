package ui.feature.createRoom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import domain.room.model.BingoType
import domain.room.model.RoomPrivacy
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.classic_bingo_type
import themedbingo.composeapp.generated.resources.create_button
import themedbingo.composeapp.generated.resources.create_room
import themedbingo.composeapp.generated.resources.name_body
import themedbingo.composeapp.generated.resources.name_textField
import themedbingo.composeapp.generated.resources.password_body
import themedbingo.composeapp.generated.resources.password_textField
import themedbingo.composeapp.generated.resources.room_name_card
import themedbingo.composeapp.generated.resources.themed_bingo_type
import ui.feature.core.CustomTopBar
import ui.feature.core.bottomSheet.UpdateBottomSheet
import ui.feature.core.buttons.CustomPrimaryButton
import ui.feature.core.cards.SingleInfoEditCard
import ui.feature.core.text.OutlinedShadowedText
import ui.feature.createRoom.screens.components.EditMaxWinnersCard
import ui.feature.createRoom.screens.components.EditPasswordCard
import ui.feature.createRoom.screens.components.EditThemeCard
import ui.theme.BingoTypeTheme
import ui.theme.GetBingoTypeBackground
import ui.util.WindowInfo
import ui.util.collectInLaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRoomScreen(
    component: CreateRoomScreenComponent,
    windowInfo: WindowInfo
) {
    val uiState by component.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showNameBottomSheet by remember { mutableStateOf(false) }
    var showPasswordBottomSheet by remember { mutableStateOf(false) }

    component.uiMessage.collectInLaunchedEffect { message ->
        snackbarHostState.showSnackbar(message)
    }

    BingoTypeTheme(
        type = uiState.type.toEnum(),
        content = {
            CreateRoomScreenContent(
                uiState = uiState,
                snackbarHostState = snackbarHostState,
                onUiEvent = component::onUiEvent,
                onNamePressed = { showNameBottomSheet = true },
                onPasswordPressed = { showPasswordBottomSheet = true },
                modifier = Modifier.fillMaxSize()
            )

            if (showNameBottomSheet) {
                UpdateBottomSheet(
                    onDismiss = { showNameBottomSheet = false },
                    onConfirm = {
                        component.onUiEvent(CreateRoomUiEvent.OnChangeName(it))
                        showNameBottomSheet = false
                    },
                    currentValue = uiState.name,
                    title = Res.string.name_textField,
                    body = Res.string.name_body,
                    label = Res.string.name_textField,
                    minLength = 4,
                    maxLength = 20,
                    accentColor = MaterialTheme.colorScheme.primary,
                    onAccentColor = MaterialTheme.colorScheme.onPrimary
                )
            }

            if (showPasswordBottomSheet) {
                UpdateBottomSheet(
                    onDismiss = { showPasswordBottomSheet = false },
                    onConfirm = { password ->
                        component.onUiEvent(
                            CreateRoomUiEvent.OnChangePrivacy(
                                RoomPrivacy.Private(password)
                            )
                        )
                        showPasswordBottomSheet = false
                    },
                    currentValue = (uiState.privacy as? RoomPrivacy.Private)?.password.orEmpty(),
                    title = Res.string.password_textField,
                    body = Res.string.password_body,
                    label = Res.string.password_textField,
                    minLength = 4,
                    maxLength = 12,
                    accentColor = MaterialTheme.colorScheme.primary,
                    onAccentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}

@Composable
private fun CreateRoomScreenContent(
    uiState: CreateRoomUiState,
    snackbarHostState: SnackbarHostState,
    onUiEvent: (CreateRoomUiEvent) -> Unit,
    onNamePressed: () -> Unit = {},
    onPasswordPressed: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            val title = when (uiState.type) {
                CreateRoomUiState.Type.Classic -> Res.string.classic_bingo_type
                is CreateRoomUiState.Type.Themed -> Res.string.themed_bingo_type
            }

            CustomTopBar(
                text = stringResource(title),
                primaryColor = MaterialTheme.colorScheme.primary,
                onPrimaryColor = MaterialTheme.colorScheme.onPrimary,
                onBackPressed = { onUiEvent(CreateRoomUiEvent.OnPopBack) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = modifier
    ) { innerPadding ->
        Box {
            Image(
                painter = GetBingoTypeBackground(uiState.type.toEnum()),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .widthIn(max = 600.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedShadowedText(
                    text = stringResource(Res.string.create_room),
                    fontSize = 24,
                    strokeWidth = 2f,
                    fontColor = MaterialTheme.colorScheme.onPrimary,
                    strokeColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 20.dp)
                )

                SingleInfoEditCard(
                    label = stringResource(Res.string.room_name_card),
                    currentValue = uiState.name,
                    onClick = onNamePressed,
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                )

                EditMaxWinnersCard(
                    currentValue = uiState.maxWinners,
                    onClick = { onUiEvent(CreateRoomUiEvent.OnChangeMaxWinners(it)) },
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 12.dp)
                )

                if (uiState.type is CreateRoomUiState.Type.Themed) {
                    EditThemeCard(
                        selectedTheme = uiState.type.theme,
                        onThemeSelected = {
                            onUiEvent(
                                CreateRoomUiEvent.OnChangeType(
                                    CreateRoomUiState.Type.Themed(it)
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 12.dp)
                    )
                }

                EditPasswordCard(
                    privacy = uiState.privacy,
                    onPasswordClick = onPasswordPressed,
                    onPrivacyChange = { onUiEvent(CreateRoomUiEvent.OnChangePrivacy(it)) },
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 24.dp)
                )
            }

            CustomPrimaryButton(
                text = stringResource(Res.string.create_button),
                enabled = uiState.canProceed,
                onClick = { onUiEvent(CreateRoomUiEvent.OnCreateRoom) },
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BingoTypeTheme(
        type = BingoType.CLASSIC,
        content = {
            CreateRoomScreenContent(
                uiState = CreateRoomUiState(isLoading = false, name = "Atumalaca"),
                snackbarHostState = SnackbarHostState(),
                onUiEvent = { },
                modifier = Modifier.fillMaxSize()
            )
        }
    )
}
