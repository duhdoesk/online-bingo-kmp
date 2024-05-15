import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import ui.navigation.RootComponent

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Themed Bingo",
    ) {
        val rootComponent = remember {
            RootComponent(DefaultComponentContext(LifecycleRegistry()))
        }

        App(rootComponent)
    }
}