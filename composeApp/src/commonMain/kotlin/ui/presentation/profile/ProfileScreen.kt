package ui.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.unmapped_error
import ui.presentation.core.ErrorScreen
import ui.presentation.core.LoadingScreen
import ui.presentation.core.dialog.GenericErrorDialog
import ui.presentation.core.dialog.GenericSuccessDialog
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.screens.PortraitProfileScreen
import ui.presentation.util.WindowInfo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileScreen(
    component: ProfileScreenComponent,
    windowInfo: WindowInfo
) {
    /**
     * Build the UI State when the UI is ready
     */
    LaunchedEffect(Unit) { component.uiEvent(ProfileScreenEvent.UILoaded) }

    /**
     * UI State
     */
    val uiState by component.uiState.collectAsState()

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
            retry = { component.uiEvent(ProfileScreenEvent.UILoaded) },
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
