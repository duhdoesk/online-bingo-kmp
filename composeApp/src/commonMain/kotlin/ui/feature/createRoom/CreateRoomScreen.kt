package ui.feature.createRoom

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
import domain.room.model.BingoType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.name_body
import themedbingo.composeapp.generated.resources.name_textField
import themedbingo.composeapp.generated.resources.password_body
import themedbingo.composeapp.generated.resources.password_textField
import ui.feature.core.RotateScreen
import ui.feature.core.bottomSheet.ThemePickerBottomSheet
import ui.feature.core.bottomSheet.UpdateBottomSheet
import ui.feature.core.dialog.GenericErrorDialog
import ui.feature.createRoom.event.CreateScreenEvent
import ui.feature.createRoom.screens.CreateClassicRoomScreen
import ui.feature.createRoom.screens.CreateThemedRoomScreen
import ui.util.WindowInfo

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateRoomScreen(
    component: CreateRoomScreenComponent,
    windowInfo: WindowInfo
) {
    /**
     * Triggers viewModel's initial data loading function
     */
    LaunchedEffect(Unit) { component.uiEvent(CreateScreenEvent.UILoaded) }

    /**
     * Coroutine Scope to handle suspend operations
     */
    val coroutineScope = rememberCoroutineScope()

    /**
     * UI State listeners
     */
    val uiState by component.uiState.collectAsState()
    val isFormOk by component.isFormOk.collectAsState()

    /**
     * Bottom Sheet State holders
     */
    val nameBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showNameBottomSheet by remember { mutableStateOf(false) }

    val passwordBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showPasswordBottomSheet by remember { mutableStateOf(false) }

    val themeBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showThemeBottomSheet by remember { mutableStateOf(false) }

    /**
     * Modal visibility listeners
     */
    val showErrorDialog = component.showErrorDialog

    /**
     * Screen calling
     */
    when (windowInfo.screenOrientation) {
        WindowInfo.DeviceOrientation.Landscape ->
            RotateScreen()

        WindowInfo.DeviceOrientation.Portrait ->
            when (uiState.bingoType) {
                BingoType.CLASSIC ->
                    CreateClassicRoomScreen(
                        uiState = uiState,
                        isFormOk = isFormOk,
                        event = { component.uiEvent(it) },
                        onEditName = { showNameBottomSheet = true },
                        onEditPassword = { showPasswordBottomSheet = true }
                    )

                BingoType.THEMED ->
                    CreateThemedRoomScreen(
                        uiState = uiState,
                        isFormOk = isFormOk,
                        event = { component.uiEvent(it) },
                        onEditTheme = { showThemeBottomSheet = true },
                        onEditName = { showNameBottomSheet = true },
                        onEditPassword = { showPasswordBottomSheet = true }
                    )
            }
    }

    /**
     * Modals
     */
    if (showErrorDialog.isVisible.value) {
        GenericErrorDialog(
            body = showErrorDialog.dialogData.value,
            onDismiss = { showErrorDialog.hideDialog() }
        )
    }

    /**
     * Bottom Sheets
     */
    if (showNameBottomSheet) {
        UpdateBottomSheet(
            sheetState = nameBottomSheetState,
            onDismiss = { showNameBottomSheet = false },
            onConfirm = {
                coroutineScope.launch {
                    delay(200)
                    component.uiEvent(CreateScreenEvent.UpdateName(it))
                    nameBottomSheetState.hide()
                    showNameBottomSheet = false
                }
            },
            currentValue = uiState.name,
            title = Res.string.name_textField,
            body = Res.string.name_body,
            label = Res.string.name_textField
        )
    }

    if (showPasswordBottomSheet) {
        UpdateBottomSheet(
            sheetState = passwordBottomSheetState,
            onDismiss = { showPasswordBottomSheet = false },
            onConfirm = {
                coroutineScope.launch {
                    delay(200)
                    component.uiEvent(CreateScreenEvent.UpdatePassword(it))
                    passwordBottomSheetState.hide()
                    showPasswordBottomSheet = false
                }
            },
            currentValue = uiState.password,
            title = Res.string.password_textField,
            body = Res.string.password_body,
            label = Res.string.password_textField
        )
    }

    if (showThemeBottomSheet) {
        ThemePickerBottomSheet(
            themes = uiState.availableThemes,
            sheetState = themeBottomSheetState,
            onThemePick = {
                coroutineScope.launch {
                    delay(200)
                    component.uiEvent(CreateScreenEvent.UpdateTheme(it))
                    themeBottomSheetState.hide()
                    showThemeBottomSheet = false
                }
            },
            onDismiss = { showThemeBottomSheet = false }
        )
    }
}
