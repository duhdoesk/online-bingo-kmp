package ui.presentation.themes

import com.arkivanov.decompose.ComponentContext

class ThemesScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit
): ComponentContext by componentContext {

    fun popBack() {
        onPopBack()
    }
}