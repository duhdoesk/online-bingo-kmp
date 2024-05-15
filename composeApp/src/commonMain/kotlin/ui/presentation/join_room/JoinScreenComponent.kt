package ui.presentation.join_room

import com.arkivanov.decompose.ComponentContext

class JoinScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit
): ComponentContext by componentContext {

    fun popBack() {
        onPopBack()
    }
}