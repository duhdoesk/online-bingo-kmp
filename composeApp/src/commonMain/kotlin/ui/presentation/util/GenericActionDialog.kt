package ui.presentation.util

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GenericActionDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: StringResource,
    body: StringResource,
) {
}