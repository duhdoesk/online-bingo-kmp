package ui.presentation.host

import com.arkivanov.decompose.ComponentContext

class HostScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit,
    val roomId: String,
): ComponentContext by componentContext {

    fun popBack() {
        onPopBack()
    }
}