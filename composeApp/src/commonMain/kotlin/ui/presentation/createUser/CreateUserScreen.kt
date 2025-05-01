@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)

package ui.presentation.createUser

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.bg_user_creation
import themedbingo.composeapp.generated.resources.nickname
import themedbingo.composeapp.generated.resources.sign_out_dialog_body
import themedbingo.composeapp.generated.resources.sign_out_dialog_title
import themedbingo.composeapp.generated.resources.update_nickname_body
import themedbingo.composeapp.generated.resources.update_nickname_title
import themedbingo.composeapp.generated.resources.update_victory_body
import themedbingo.composeapp.generated.resources.update_victory_title
import themedbingo.composeapp.generated.resources.victory_message
import ui.presentation.core.bottomSheet.UpdateBottomSheet
import ui.presentation.core.bottomSheet.UpdatePictureBottomSheet
import ui.presentation.core.buttons.QuitAndPrimaryButtonRow
import ui.presentation.core.cards.SingleInfoEditCard
import ui.presentation.core.dialog.GenericActionDialog
import ui.presentation.core.dialog.GenericErrorDialog
import ui.presentation.createUser.component.CreateUserHeaderTexts
import ui.presentation.createUser.component.UserPictureBox
import ui.presentation.util.collectInLaunchedEffect
import ui.theme.AppTheme
import ui.theme.createUserPrimaryColor

@Composable
fun CreateUserScreen(viewModel: CreateUserComponent) {
    /** Screen State listener */
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var errorMessage: MutableState<String?> = remember { mutableStateOf(null) }

    viewModel.uiError.collectInLaunchedEffect { error ->
        errorMessage.value = error.message
    }

    /**
     * Coroutine Scope
     */
    val coroutineScope = rememberCoroutineScope()

    /**
     * Update Bottom Sheets visibility holders
     */
    val bottomSheetState = rememberModalBottomSheetState(true)
    var showEditNameBottomSheet by remember { mutableStateOf(false) }
    var showEditMessageBottomSheet by remember { mutableStateOf(false) }
    var showEditPictureBottomSheet by remember { mutableStateOf(false) }
    var showQuitDialog by remember { mutableStateOf(false) }

    CreateUserScreen(
        uiState = uiState,
        onUiEvent = { viewModel.onUiEvent(it) },
        modifier = Modifier.fillMaxSize(),
        onQuit = { showQuitDialog = true },
        onEditPicture = { showEditPictureBottomSheet = true },
        onEditName = { showEditNameBottomSheet = true },
        onEditMessage = { showEditMessageBottomSheet = true }
    )

    /** Show error if there is any */
    if (errorMessage.value != null) {
        GenericErrorDialog(
            onDismiss = { errorMessage.value = null },
            body = errorMessage.value
        )
    }

    /**
     * Visibility of dialogs and bottom sheets
     */
    if (showEditNameBottomSheet) {
        UpdateBottomSheet(
            onDismiss = { coroutineScope.launch { showEditNameBottomSheet = false } },
            onConfirm = {
                coroutineScope.launch {
                    bottomSheetState.hide()
                    showEditNameBottomSheet = false
                    viewModel.onUiEvent(CreateUserUiEvent.UpdateName(it))
                }
            },
            currentValue = uiState.name,
            title = Res.string.update_nickname_title,
            body = Res.string.update_nickname_body,
            label = Res.string.nickname,
            sheetState = bottomSheetState
        )
    }

    if (showEditMessageBottomSheet) {
        UpdateBottomSheet(
            onDismiss = { coroutineScope.launch { showEditMessageBottomSheet = false } },
            onConfirm = {
                coroutineScope.launch {
                    bottomSheetState.hide()
                    showEditMessageBottomSheet = false
                    viewModel.onUiEvent(CreateUserUiEvent.UpdateVictoryMessage(it))
                }
            },
            currentValue = uiState.message,
            title = Res.string.update_victory_title,
            body = Res.string.update_victory_body,
            label = Res.string.victory_message,
            sheetState = bottomSheetState
        )
    }

    if (showEditPictureBottomSheet) {
        UpdatePictureBottomSheet(
            sheetState = bottomSheetState,
            currentName = uiState.name,
            currentPicture = uiState.pictureUri,
            availablePictures = uiState.profilePictures,
            onUpdatePicture = { viewModel.onUiEvent(CreateUserUiEvent.UpdatePicture(it)) },
            onHide = {
                coroutineScope.launch {
                    bottomSheetState.hide()
                    showEditPictureBottomSheet = false
                }
            }
        )
    }

    if (showQuitDialog) {
        GenericActionDialog(
            onDismiss = { showQuitDialog = false },
            onConfirm = {
                showQuitDialog = false
                viewModel.onUiEvent(CreateUserUiEvent.SignOut)
            },
            title = Res.string.sign_out_dialog_title,
            body = Res.string.sign_out_dialog_body
        )
    }
}

@Composable
private fun CreateUserScreen(
    uiState: CreateUserUiState,
    onUiEvent: (CreateUserUiEvent) -> Unit,
    onQuit: () -> Unit = {},
    onEditPicture: () -> Unit = {},
    onEditName: () -> Unit = {},
    onEditMessage: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(Res.drawable.bg_user_creation),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        modifier = modifier,
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .widthIn(600.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                CreateUserHeaderTexts(
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                UserPictureBox(
                    pictureUri = uiState.pictureUri,
                    onEditPicture = onEditPicture,
                    modifier = Modifier
                        .padding(vertical = 40.dp)
                        .size(160.dp)
                )

                SingleInfoEditCard(
                    label = stringResource(Res.string.nickname),
                    currentValue = uiState.name,
                    onClick = onEditName,
                    contentColor = createUserPrimaryColor,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                SingleInfoEditCard(
                    label = stringResource(Res.string.victory_message),
                    currentValue = uiState.message,
                    onClick = onEditMessage,
                    contentColor = createUserPrimaryColor,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                )
            }

            QuitAndPrimaryButtonRow(
                primaryEnabled = uiState.canProceed,
                onPrimaryClick = { onUiEvent(CreateUserUiEvent.CreateUser) },
                onQuitClick = onQuit,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun CreateUserScreenPreview() {
    AppTheme {
        CreateUserScreen(
            uiState = CreateUserUiState(
                canProceed = true,
                name = "Bingo Friend",
                message = "Themed Bingo is awesome!"
            ),
            onUiEvent = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
