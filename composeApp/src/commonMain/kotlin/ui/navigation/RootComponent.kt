package ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import dev.gitlive.firebase.auth.FirebaseUser
import domain.auth.AuthService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.presentation.change_password.ChangePasswordScreenComponent
import ui.presentation.create_room.CreateRoomScreenComponent
import ui.presentation.forgot_password.ForgotPasswordScreenComponent
import ui.presentation.home.HomeScreenComponent
import ui.presentation.host.HostScreenComponent
import ui.presentation.join_room.JoinScreenComponent
import ui.presentation.play.PlayScreenComponent
import ui.presentation.profile.ProfileScreenComponent
import ui.presentation.profile.picture.EditProfilePictureScreenComponent
import ui.presentation.sign_in.SignInScreenComponent
import ui.presentation.sign_up.SignUpScreenComponent
import ui.presentation.themes.ThemesScreenComponent

class RootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinComponent {

    private val authService by inject<AuthService>()

    private val firebaseUser: FirebaseUser?
        get() = authService.currentUser

    private val navigation = StackNavigation<Configuration>()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.SignInScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun signIn() {
        navigation.replaceCurrent(Configuration.HomeScreen)
    }

    private fun signOut() {
        navigation.replaceAll(Configuration.SignInScreen)
    }

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
                    firebaseUser = firebaseUser!!,
                    onPopBack = { navigation.pop() },
                    onSignOut = { signOut() },
                    onUpdatePicture = {
                        navigation.pushNew(configuration = Configuration.EditProfilePictureScreen)
                    },
                    onUpdatePassword = {
                        navigation.pushNew(configuration = Configuration.ChangePasswordScreen)
                    }
                )
            )

            Configuration.SignInScreen -> Child.SignInScreen(
                SignInScreenComponent(
                    componentContext = context,
                    onSignIn = { signIn() },
                    onSignUp = { navigation.pushNew(configuration = Configuration.SignUpScreen) },
                    onPasswordReset = { navigation.pushNew(configuration = Configuration.ForgotPasswordScreen) },
                    firebaseUser = firebaseUser,
                )
            )

            Configuration.SignUpScreen -> Child.SignUpScreen(
                SignUpScreenComponent(
                    componentContext = context,
                    onSignUp = { signIn() },
                    onPopBack = { navigation.pop() }
                )
            )

            Configuration.ForgotPasswordScreen -> Child.ForgotPasswordScreen(
                ForgotPasswordScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                )
            )

            Configuration.EditProfilePictureScreen -> Child.EditProfilePictureScreen(
                EditProfilePictureScreenComponent(
                    componentContext = context,
                    firebaseUser = firebaseUser,
                    onCancel = { navigation.pop() },
                    onPictureSaved = { navigation.pop() }
                )
            )

            Configuration.ChangePasswordScreen -> Child.ChangePasswordScreen(
                ChangePasswordScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                )
            )
        }
    }
}