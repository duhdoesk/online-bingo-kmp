import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.navigation.Child
import ui.navigation.RootComponent
import ui.presentation.home.HomeScreen
import ui.presentation.themes.ThemesScreen

@Composable
@Preview
fun App(rootComponent: RootComponent) {
    MaterialTheme {

        val childStack by rootComponent.childStack.subscribeAsState()

        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is Child.HomeScreen -> HomeScreen(instance.component)
                is Child.ThemesScreen -> ThemesScreen(instance.component)
            }
        }
    }
}