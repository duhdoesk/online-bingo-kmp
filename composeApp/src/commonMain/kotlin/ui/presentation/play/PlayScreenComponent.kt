package ui.presentation.play

import com.arkivanov.decompose.ComponentContext

class PlayScreenComponent(
    componentContext: ComponentContext,
    val roomId: String,
    private val onPopBack: () -> Unit
): ComponentContext by componentContext {

    fun popBack() {
        onPopBack()
    }
}