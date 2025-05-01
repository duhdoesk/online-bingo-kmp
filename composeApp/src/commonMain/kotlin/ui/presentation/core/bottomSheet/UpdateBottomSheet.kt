package ui.presentation.core.bottomSheet

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UpdateBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: () -> Unit,
    onConfirm: (newData: String) -> Unit,
    currentValue: String,
    title: StringResource,
    body: StringResource,
    label: StringResource,
    needsTrim: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var data by remember { mutableStateOf(TextFieldValue(currentValue)) }
    val isValid by remember(data) { mutableStateOf(data.text.length >= 3) }

    UpdateBottomSheetContent(
        sheetState = sheetState,
        title = title,
        body = body,
        label = label,
        value = data,
        isValid = isValid,
        onValueUpdate = { data = if (needsTrim) TextFieldValue(it.text.trim()) else it },
        onConfirm = {
            keyboardController?.hide()
            onConfirm(data.text)
        },
        onCancel = {
            keyboardController?.hide()
            onDismiss()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
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
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(body),
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

            BottomSheetButtons(
                onCancel = onCancel,
                onConfirm = onConfirm,
                canConfirm = isValid,
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 16.dp)
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
