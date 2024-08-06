package ui.presentation.change_password

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.error
import themedbingo.composeapp.generated.resources.success
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

    if (component.successDialog.isVisible.value) {
        GenericSuccessDialog(
            stringRes = Res.string.success, //todo(): change
            onDismiss = { component.successDialog.hideDialog() },
        )
    }

    if (component.errorDialog.isVisible.value) {
        GenericErrorDialog(
            onDismiss = { component.errorDialog.hideDialog() },
            body = null, //todo(): change
        )
    }
}