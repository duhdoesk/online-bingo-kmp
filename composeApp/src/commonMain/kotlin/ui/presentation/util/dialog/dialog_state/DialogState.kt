package ui.presentation.util.dialog.dialog_state

import androidx.compose.runtime.State

sealed interface DialogState<T> {
    val dialogData: State<T>
    val isVisible: State<Boolean>
}

sealed interface MutableDialogState<T> : DialogState<T> {
    override val dialogData: State<T>
    override val isVisible: State<Boolean>

    fun showDialog(data: T)
    fun hideDialog()
}