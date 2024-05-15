package ui.presentation.home

import com.arkivanov.decompose.ComponentContext

class HomeScreenComponent(
    componentContext: ComponentContext,
    private val onNavigate: () -> Unit
): ComponentContext by componentContext {

    fun navigate() {
        onNavigate()
    }
}