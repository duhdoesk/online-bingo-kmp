package ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import ui.presentation.create_room.CreateScreenComponent
import ui.presentation.home.HomeScreenComponent
import ui.presentation.host.HostScreenComponent
import ui.presentation.join_room.JoinScreenComponent
import ui.presentation.play.PlayScreenComponent
import ui.presentation.profile.ProfileScreen
import ui.presentation.profile.ProfileScreenComponent
import ui.presentation.themes.ThemesScreenComponent

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.HomeScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        configuration: Configuration,
        context: ComponentContext
    ): Child {
        return when (configuration) {
            Configuration.HomeScreen -> Child.HomeScreen(
                HomeScreenComponent(
                    componentContext = context,
                    onNavigate = { receivedConfig ->
                        navigation.pushNew(configuration = receivedConfig)
                    }
                )
            )

            Configuration.ThemesScreen -> Child.ThemesScreen(
                ThemesScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() }
                )
            )

            Configuration.CreateScreen -> Child.CreateScreen(
                CreateScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() }
                )
            )

            Configuration.HostScreen -> Child.HostScreen(
                HostScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() }
                )
            )

            Configuration.JoinScreen -> Child.JoinScreen(
                JoinScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() }
                )
            )

            Configuration.PlayScreen -> Child.PlayScreen(
                PlayScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() }
                )
            )

            Configuration.ProfileScreen -> Child.ProfileScreen(
                ProfileScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() }
                )
            )
        }
    }
}