package ui.presentation.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.delete_account_body
import themedbingo.composeapp.generated.resources.delete_account_title
import themedbingo.composeapp.generated.resources.nickname
import themedbingo.composeapp.generated.resources.sign_out_dialog_body
import themedbingo.composeapp.generated.resources.sign_out_dialog_title
import themedbingo.composeapp.generated.resources.unmapped_error
import themedbingo.composeapp.generated.resources.update_nickname_body
import themedbingo.composeapp.generated.resources.update_nickname_title
import themedbingo.composeapp.generated.resources.update_victory_body
import themedbingo.composeapp.generated.resources.update_victory_title
import themedbingo.composeapp.generated.resources.victory_message
import ui.presentation.common.ErrorScreen
import ui.presentation.common.LoadingScreen
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.screens.PortraitProfileScreen
import ui.presentation.util.WindowInfo
import ui.presentation.util.bottom_sheet.UpdateBottomSheet
import ui.presentation.util.dialog.GenericActionDialog
import ui.presentation.util.dialog.GenericErrorDialog
import ui.presentation.util.dialog.GenericSuccessDialog

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    component: ProfileScreenComponent,
    windowInfo: WindowInfo,
) {

    /**
     * Build the UI State when the UI is ready
     */
    LaunchedEffect(Unit) { component.uiEvent(ProfileScreenEvent.UILoaded) }

    /**
     * Single Coroutine Scope to handle suspend UI operations
     */
    val coroutineScope = rememberCoroutineScope()

    /**
     * UI State
     */
    val uiState by component.uiState.collectAsState()
    val user = uiState.user

    /**
     * Result dialogs, held by the viewmodel / component
     */
    val successState = component.successDialogState
    val errorState = component.errorDialogState

    /**
     * Action dialogs and bottom sheets
     */
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }
    val nameBottomSheetState = rememberModalBottomSheetState()
    val messageBottomSheetState = rememberModalBottomSheetState()

    /**
     * Screen calling based on UI State
     */
    if (uiState.isLoading) {
        LoadingScreen()
        return
    }

    when (user) {
        null -> {
            ErrorScreen(
                retry = { component.uiEvent(ProfileScreenEvent.UILoaded) },
                popBack = { component.uiEvent(ProfileScreenEvent.PopBack) },
                message = Res.string.unmapped_error,
            )
        }

        else -> {
            PortraitProfileScreen(
                user = user,
                event = { component.uiEvent(it) },
                onUpdateName = { coroutineScope.launch { nameBottomSheetState.show() } },
                onUpdateMessage = { coroutineScope.launch { messageBottomSheetState.show() } },
                onSignOut = { showSignOutDialog = true },
                onDeleteAccount = { showDeleteAccountDialog = true },
            )
        }
    }

    /**
     * Visibility of dialogs and bottom sheets
     */
    if (nameBottomSheetState.isVisible) {
        UpdateBottomSheet(
            onDismiss = { coroutineScope.launch { nameBottomSheetState.hide() } },
            onConfirm = {
                coroutineScope.launch {
                    nameBottomSheetState.hide()
                    component.uiEvent(ProfileScreenEvent.UpdateName(it))
                }
            },
            currentData = uiState.user?.name.orEmpty(),
            title = Res.string.update_nickname_title,
            body = Res.string.update_nickname_body,
            label = Res.string.nickname,
        )
    }

    if (messageBottomSheetState.isVisible) {
        UpdateBottomSheet(
            onDismiss = { coroutineScope.launch { messageBottomSheetState.hide() } },
            onConfirm = {
                coroutineScope.launch {
                    messageBottomSheetState.hide()
                    component.uiEvent(ProfileScreenEvent.UpdateMessage(it))
                }
            },
            currentData = uiState.user?.victoryMessage.orEmpty(),
            title = Res.string.update_victory_title,
            body = Res.string.update_victory_body,
            label = Res.string.victory_message,
        )
    }

    if (showSignOutDialog) {
        GenericActionDialog(
            onDismiss = { showSignOutDialog = false },
            onConfirm = {
                showSignOutDialog = false
                component.uiEvent(ProfileScreenEvent.SignOut)
            },
            title = Res.string.sign_out_dialog_title,
            body = Res.string.sign_out_dialog_body,
        )
    }

    if (showDeleteAccountDialog) {
        GenericActionDialog(
            onDismiss = { showDeleteAccountDialog = false },
            onConfirm = {
                showDeleteAccountDialog = false
                component.uiEvent(ProfileScreenEvent.DeleteAccount)
            },
            title = Res.string.delete_account_title,
            body = Res.string.delete_account_body,
        )
    }

    if (successState.isVisible.value) {
        GenericSuccessDialog(
            onDismiss = { successState.hideDialog() },
            body = successState.dialogData.value,
        )
    }

    if (errorState.isVisible.value) {
        GenericErrorDialog(
            onDismiss = { errorState.hideDialog() },
            body = errorState.dialogData.value,
        )
    }
}

