package ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import domain.user.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ui.presentation.create_room.CreateRoomScreenComponent
import ui.presentation.home.HomeScreenComponent
import ui.presentation.host.HostScreenComponent
import ui.presentation.join_room.JoinScreenComponent
import ui.presentation.play.PlayScreenComponent
import ui.presentation.profile.ProfileScreenComponent
import ui.presentation.sign_in.SignInScreenComponent
import ui.presentation.themes.ThemesScreenComponent

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val user = MutableStateFlow<User?>(null)

    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.SignInScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun signIn(signedUser: User) {
        user.update { signedUser }
        navigation.replaceCurrent(Configuration.HomeScreen)
    }

    private fun signOut() {
        user.update { null }
        navigation.replaceAll(Configuration.SignInScreen)
    }

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
                CreateRoomScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                    onCreateRoom = { receivedConfig ->
                        navigation.replaceCurrent(configuration = receivedConfig)
                    }
                )
            )

            is Configuration.HostScreen -> Child.HostScreen(
                HostScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                    roomId = configuration.roomId
                )
            )

            Configuration.JoinScreen -> Child.JoinScreen(
                JoinScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                    onJoinRoom = { receivedConfig ->
                        navigation.replaceCurrent(configuration = receivedConfig)
                    }
                )
            )

            is Configuration.PlayScreen -> Child.PlayScreen(
                PlayScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                    roomId = configuration.roomId
                )
            )

            Configuration.ProfileScreen -> Child.ProfileScreen(
                ProfileScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                    onSignOut = { signOut() }
                )
            )

            Configuration.SignInScreen -> Child.SignInScreen(
                SignInScreenComponent(
                    componentContext = context,
                    onSignIn = { signedUser -> signIn(signedUser) }
                )
            )
        }
    }
}