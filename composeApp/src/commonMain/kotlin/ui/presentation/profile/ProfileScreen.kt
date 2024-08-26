package ui.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.delete_account_body
import themedbingo.composeapp.generated.resources.delete_account_title
import themedbingo.composeapp.generated.resources.nickname
import themedbingo.composeapp.generated.resources.sign_out_dialog_body
import themedbingo.composeapp.generated.resources.sign_out_dialog_title
import themedbingo.composeapp.generated.resources.update_nickname_body
import themedbingo.composeapp.generated.resources.update_nickname_title
import themedbingo.composeapp.generated.resources.update_victory_body
import themedbingo.composeapp.generated.resources.update_victory_title
import themedbingo.composeapp.generated.resources.user_data_not_found
import themedbingo.composeapp.generated.resources.victory_message
import ui.presentation.common.ErrorScreen
import ui.presentation.common.LoadingScreen
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.screens.ProfileScreenOrientation
import ui.presentation.profile.state.ProfileScreenUIState
import ui.presentation.util.WindowInfo
import ui.presentation.util.bottom_sheet.UpdateBottomSheet
import ui.presentation.util.dialog.GenericActionDialog
import ui.presentation.util.dialog.GenericErrorDialog
import ui.presentation.util.dialog.GenericSuccessDialog

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(component: ProfileScreenComponent, windowInfo: WindowInfo) {

    val uiState = component
        .profileScreenUiState
        .collectAsState()
        .value

    val successState = component.successDialogState
    val errorState = component.errorDialogState
    val updateNameState = component.updateNameDialogState
    val updateVictoryMessageState = component.updateVictoryMessageDialogState
    val signOutState = component.signOutDialogState
    val deleteAccountState = component.deleteAccountDialogState

    Scaffold { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            when (uiState) {
                ProfileScreenUIState.Error ->
                    ErrorScreen(
                        message = Res.string.user_data_not_found,
                        retry = { },
                        popBack = { component.popBack() },
                    )

                ProfileScreenUIState.Loading ->
                    LoadingScreen()

                is ProfileScreenUIState.Success ->
                    ProfileScreenOrientation(
                        windowInfo = windowInfo,
                        uiState = uiState,
                    ) { profileScreenEvent ->
                        when (profileScreenEvent) {
                            ProfileScreenEvent.DeleteAccount ->
                                deleteAccountState.showDialog(null)

                            ProfileScreenEvent.PopBack ->
                                component.popBack()

                            ProfileScreenEvent.SignOut ->
                                signOutState.showDialog(null)

                            ProfileScreenEvent.UpdateName ->
                                updateNameState.showDialog(uiState.user.name)

                            ProfileScreenEvent.UpdatePassword ->
                                component.updatePassword()

                            ProfileScreenEvent.UpdatePicture ->
                                component.updatePicture()

                            ProfileScreenEvent.UpdateVictoryMessage ->
                                updateVictoryMessageState.showDialog(uiState.user.victoryMessage)
                        }
                    }
            }
        }

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

    if (updateNameState.isVisible.value) {
        UpdateBottomSheet(
            onDismiss = { updateNameState.hideDialog() },
            onConfirm = {
                updateNameState.hideDialog()
                component.updateName(it)
            },
            currentData = updateNameState.dialogData.value,
            title = Res.string.update_nickname_title,
            body = Res.string.update_nickname_body,
            label = Res.string.nickname,
        )
    }

    if (updateVictoryMessageState.isVisible.value) {
        UpdateBottomSheet(
            onDismiss = { updateVictoryMessageState.hideDialog() },
            onConfirm = {
                updateVictoryMessageState.hideDialog()
                component.updateVictoryMessage(it)
            },
            currentData = updateVictoryMessageState.dialogData.value,
            title = Res.string.update_victory_title,
            body = Res.string.update_victory_body,
            label = Res.string.victory_message,
        )
    }

    if (signOutState.isVisible.value) {
        GenericActionDialog(
            onDismiss = { signOutState.hideDialog() },
            onConfirm = {
                signOutState.hideDialog()
                component.signOut()
            },
            title = Res.string.sign_out_dialog_title,
            body = Res.string.sign_out_dialog_body,
        )
    }

    if (deleteAccountState.isVisible.value) {
        GenericActionDialog(
            onDismiss = { deleteAccountState.hideDialog() },
            onConfirm = {
                deleteAccountState.hideDialog()
                component.deleteAccount()
            },
            title = Res.string.delete_account_title,
            body = Res.string.delete_account_body,
        )
    }
}