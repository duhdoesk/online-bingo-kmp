package ui.presentation.host

import com.arkivanov.decompose.ComponentContext
import util.componentCoroutineScope

class HostScreenComponent(
    componentContext: ComponentContext,
    private val onPopBack: () -> Unit,
    val roomId: String,
): ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()

    fun popBack() {
        onPopBack()
    }
}