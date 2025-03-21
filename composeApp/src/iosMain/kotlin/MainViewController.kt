import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.navigation.RootComponent

@ExperimentalResourceApi
fun MainViewController() = ComposeUIViewController {
    val rootComponent = remember {
        RootComponent(DefaultComponentContext(LifecycleRegistry()))
    }

    App(rootComponent)
}
