package ui.presentation.host

import com.arkivanov.decompose.ComponentContext

class HostScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit
): ComponentContext by componentContext {

    fun popBack() {
        onPopBack()
    }
}