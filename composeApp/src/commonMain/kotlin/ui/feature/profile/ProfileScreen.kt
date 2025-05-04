package ui.feature.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.unmapped_error
import ui.feature.core.ErrorScreen
import ui.feature.core.LoadingScreen
import ui.feature.core.dialog.GenericErrorDialog
import ui.feature.core.dialog.GenericSuccessDialog
import ui.feature.profile.event.ProfileScreenEvent
import ui.feature.profile.screens.PortraitProfileScreen
import ui.util.WindowInfo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileScreen(
    component: ProfileScreenComponent,
    windowInfo: WindowInfo
) {
    /**
     * UI State
     */
    val uiState by component.uiState.collectAsStateWithLifecycle()

    /**
     * Result dialogs, held by the viewmodel / component
     */
    val successState = component.successDialogState
    val errorState = component.errorDialogState

    /**
     * Screen calling based on UI State
     */
    if (uiState.isLoading) {
        LoadingScreen()
        return
    }

    if (uiState.error) {
        ErrorScreen(
            retry = { },
            popBack = { component.uiEvent(ProfileScreenEvent.PopBack) },
            message = Res.string.unmapped_error
        )
    } else {
        PortraitProfileScreen(
            user = uiState.user!!,
            profilePictures = uiState.profilePictures,
            event = { component.uiEvent(it) }
        )
    }

    /**
     * Visibility of dialogs and bottom sheets
     */
    if (successState.isVisible.value) {
        GenericSuccessDialog(
            onDismiss = { successState.hideDialog() },
            body = successState.dialogData.value
        )
    }

    if (errorState.isVisible.value) {
        GenericErrorDialog(
            onDismiss = { errorState.hideDialog() },
            body = errorState.dialogData.value
        )
    }
}
