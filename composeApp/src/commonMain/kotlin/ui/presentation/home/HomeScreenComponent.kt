package ui.presentation.home

import com.arkivanov.decompose.ComponentContext
import ui.navigation.Configuration

class HomeScreenComponent(
    componentContext: ComponentContext,
    private val onNavigate: (configuration: Configuration) -> Unit
): ComponentContext by componentContext {

    fun navigate(configuration: Configuration) {
        onNavigate(configuration)
    }
}