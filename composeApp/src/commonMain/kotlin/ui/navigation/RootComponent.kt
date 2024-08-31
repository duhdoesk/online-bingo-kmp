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
import domain.auth.supabase.SupabaseAuthService
import domain.user.use_case.CreateUserUseCase
import domain.user.use_case.GetUserByIdUseCase
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.presentation.change_password.ChangePasswordScreenComponent
import ui.presentation.create_room.CreateRoomScreenComponent
import ui.presentation.forgot_password.ForgotPasswordScreenComponent
import ui.presentation.home.HomeScreenComponent
import ui.presentation.join_room.JoinScreenComponent
import ui.presentation.profile.ProfileScreenComponent
import ui.presentation.profile.picture.EditProfilePictureScreenComponent
import ui.presentation.room.classic.host.ClassicHostScreenComponent
import ui.presentation.room.classic.play.ClassicPlayScreenComponent
import ui.presentation.room.themed.host.HostScreenComponent
import ui.presentation.room.themed.play.PlayScreenComponent
import ui.presentation.sign_in.SignInScreenComponent
import ui.presentation.sign_up.SignUpScreenComponent
import ui.presentation.themes.ThemesScreenComponent

class RootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinComponent {

    private val authService by inject<AuthService>()
    private val supabaseAuthService by inject<SupabaseAuthService>()
    private val getUserByIdUseCase by inject<GetUserByIdUseCase>()
    private val createUserUseCase by inject<CreateUserUseCase>()

    private val supabaseClient = supabaseAuthService.supabaseClient

    private val firebaseUser: FirebaseUser?
        get() = authService.currentUser

    val user = supabaseClient.auth.sessionStatus.map { sessionStatus ->
        when (sessionStatus) {
            is SessionStatus.Authenticated -> {
                if (sessionStatus.isNew) {
                    createUserUseCase(
                        id = sessionStatus.session.user?.id.orEmpty(),
                        email = sessionStatus.session.user?.email.orEmpty(),
                    )
                }
                getUserByIdUseCase(sessionStatus.session.user?.id.orEmpty()).getOrNull()
            }
            else -> null
        }
    }

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
                    user = user,
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

            is Configuration.CreateScreen -> Child.CreateScreen(
                CreateRoomScreenComponent(
                    componentContext = context,
                    firebaseUser = firebaseUser!!,
                    bingoType = configuration.bingoType,
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

            is Configuration.JoinScreen -> Child.JoinScreen(
                JoinScreenComponent(
                    componentContext = context,
                    firebaseUser = firebaseUser!!,
                    bingoType = configuration.bingoType,
                    onPopBack = { navigation.pop() },
                    onJoinRoom = { config -> navigation.replaceCurrent(configuration = config) },
                    onCreateRoom = {
                        navigation.replaceCurrent(
                            Configuration.CreateScreen(
                                configuration.bingoType
                            )
                        )
                    }
                )
            )

            is Configuration.PlayScreen -> Child.PlayScreen(
                PlayScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                    roomId = configuration.roomId,
                    firebaseUser = firebaseUser!!,
                )
            )

            Configuration.ProfileScreen -> Child.ProfileScreen(
                ProfileScreenComponent(
                    componentContext = context,
                    user = user,
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
                    user = user,
                    supabaseClient = supabaseClient,
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

            is Configuration.ClassicHostScreen -> Child.ClassicHostScreen(
                ClassicHostScreenComponent(
                    componentContext = context,
                    roomId = configuration.roomId,
                    onPopBack = { navigation.pop() }
                )
            )

            is Configuration.ClassicPlayScreen -> Child.ClassicPlayScreen(
                ClassicPlayScreenComponent(
                    componentContext = context,
                    firebaseUser = firebaseUser!!,
                    roomId = configuration.roomId,
                    onPopBack = { navigation.pop() },
                )
            )
        }
    }
}