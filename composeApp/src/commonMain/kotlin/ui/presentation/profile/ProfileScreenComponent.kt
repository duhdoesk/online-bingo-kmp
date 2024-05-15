package ui.presentation.profile

import com.arkivanov.decompose.ComponentContext

class ProfileScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit
): ComponentContext by componentContext {

    fun popBack() {
        onPopBack()
    }
}