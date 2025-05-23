@file:OptIn(ExperimentalMaterial3Api::class)

package ui.feature.core.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.confirm_button
import ui.feature.core.buttons.QuitAndPrimaryButtonRow
import ui.feature.core.text.OutlinedShadowedText
import ui.theme.error
import ui.theme.homeOnColor
import ui.theme.homePrimaryColor

@Composable
fun UpdateBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: () -> Unit,
    onConfirm: (newData: String) -> Unit,
    currentValue: String,
    title: StringResource,
    body: StringResource,
    label: StringResource,
    maxLength: Int = Int.MAX_VALUE,
    minLength: Int = 3
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var data by remember {
        mutableStateOf(
            TextFieldValue(
                currentValue.let {
                    if (it.length < maxLength) it else it.take(maxLength - 1)
                }
            )
        )
    }
    val isValid = data.text.length >= minLength && data.text != currentValue

    UpdateBottomSheetContent(
        sheetState = sheetState,
        title = title,
        body = body,
        label = label,
        value = data,
        isValid = isValid,
        onValueUpdate = {
            data = if (it.text.length <= maxLength) it else data
        },
        onConfirm = {
            keyboardController?.hide()
            onConfirm(data.text.trim())
        },
        onCancel = {
            keyboardController?.hide()
            onDismiss()
        }
    )
}

@Composable
private fun UpdateBottomSheetContent(
    sheetState: SheetState,
    title: StringResource,
    body: StringResource,
    label: StringResource,
    value: TextFieldValue,
    isValid: Boolean,
    onValueUpdate: (value: TextFieldValue) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { onCancel() },
        sheetState = sheetState,
        contentWindowInsets = { WindowInsets.ime }
    ) {
        Column {
            OutlinedShadowedText(
                text = stringResource(title),
                fontSize = 24,
                strokeWidth = 2f,
                fontColor = homePrimaryColor,
                strokeColor = homeOnColor,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(body),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            UpdateBottomSheetTextField(
                value = value,
                label = label,
                onValueUpdate = onValueUpdate,
                onConfirm = onConfirm,
                isValid = isValid,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(32.dp))

            QuitAndPrimaryButtonRow(
                primaryButtonColors = ButtonDefaults.buttonColors(
                    containerColor = homePrimaryColor,
                    contentColor = homeOnColor
                ),
                iconButtonColors = ButtonDefaults.buttonColors(
                    containerColor = homeOnColor,
                    contentColor = error
                ),
                primaryText = stringResource(Res.string.confirm_button),
                primaryEnabled = isValid,
                quitEnabled = true,
                onPrimaryClick = onConfirm,
                onQuitClick = onCancel,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )

            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun UpdateBottomSheetTextField(
    value: TextFieldValue,
    label: StringResource,
    isValid: Boolean,
    onValueUpdate: (value: TextFieldValue) -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueUpdate,
        label = { Text(stringResource(label)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (isValid) onConfirm()
            }
        ),
        modifier = modifier.fillMaxWidth()
    )
}
