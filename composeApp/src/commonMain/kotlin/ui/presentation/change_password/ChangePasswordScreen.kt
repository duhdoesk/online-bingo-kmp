package ui.presentation.change_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.presentation.change_password.event.ChangePasswordScreenUIEvent
import ui.presentation.change_password.screens.PortraitChangePasswordScreen
import ui.presentation.common.RotateScreen
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.GenericErrorDialog
import ui.presentation.util.dialog.GenericSuccessDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChangePasswordScreen(
    component: ChangePasswordScreenComponent,
    windowInfo: WindowInfo
) {

    val uiState by component.changePasswordScreenUIState.collectAsState()
    val isFormValid = component.isFormValid

    Scaffold { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (windowInfo.screenOrientation) {
                WindowInfo.DeviceOrientation.Landscape ->
                    RotateScreen()

                WindowInfo.DeviceOrientation.Portrait ->
                    PortraitChangePasswordScreen(
                        currentPassword = uiState.currentPassword,
                        newPassword = uiState.newPassword,
                        repeatPassword = uiState.repeatPassword,
                        isFormValid = isFormValid,
                        event = { event ->
                            when (event) {
                                ChangePasswordScreenUIEvent.ConfirmPasswordChange ->
                                    component.changePassword()

                                ChangePasswordScreenUIEvent.PopBack ->
                                    component.popBack()

                                is ChangePasswordScreenUIEvent.CurrentPasswordTyping ->
                                    component.currentPasswordTyping(event.input)

                                is ChangePasswordScreenUIEvent.NewPasswordTyping ->
                                    component.newPasswordTyping(event.input)

                                is ChangePasswordScreenUIEvent.RepeatPasswordTyping ->
                                    component.repeatPasswordTyping(event.input)
                            }
                        },
                    )
            }
        }
    }

    if (component.successDialog.isVisible.value) {
        GenericSuccessDialog(
            onDismiss = {
                component.successDialog.hideDialog()
                component.popBack()
            },
            body = component.successDialog.dialogData.value,
        )
    }

//    if (component.errorDialog.isVisible.value) {
//        GenericErrorDialog(
//            onDismiss = { component.errorDialog.hideDialog() },
//            body = component.errorDialog.dialogData.value,
//        )
//    }
}