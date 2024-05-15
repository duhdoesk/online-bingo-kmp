package ui.presentation.create_room

import com.arkivanov.decompose.ComponentContext

class CreateScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit
): ComponentContext by componentContext {

    fun popBack() {
        onPopBack()
    }
}