package ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.revenuecat.purchases.kmp.CustomerInfo
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.configure
import domain.auth.supabase.SupabaseAuthService
import domain.billing.SubscribeToUserSubscriptionData
import domain.user.use_case.CheckIfIsNewUserUseCase
import domain.user.use_case.GetUserByIdUseCase
import getPlatform
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.presentation.change_password.ChangePasswordScreenComponent
import ui.presentation.create_room.CreateRoomScreenComponent
import ui.presentation.forgot_password.ForgotPasswordScreenComponent
import ui.presentation.home.HomeScreenComponent
import ui.presentation.join_room.JoinScreenComponent
import ui.presentation.paywall.PaywallScreenViewModel
import ui.presentation.profile.ProfileScreenComponent
import ui.presentation.profile.picture.EditProfilePictureScreenComponent
import ui.presentation.room.classic.host.ClassicHostScreenComponent
import ui.presentation.room.classic.play.ClassicPlayScreenComponent
import ui.presentation.room.themed.host.HostScreenComponent
import ui.presentation.room.themed.play.PlayScreenComponent
import ui.presentation.sign_in.SignInScreenComponent
import ui.presentation.sign_up.SignUpScreenComponent
import ui.presentation.themes.ThemesScreenComponent
import util.componentCoroutineScope

class RootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentContext.componentCoroutineScope()

    /**
     * Use Cases
     */
    private val supabaseAuthService by inject<SupabaseAuthService>()
    private val getUserByIdUseCase by inject<GetUserByIdUseCase>()
    private val checkIfIsNewUserUseCase by inject<CheckIfIsNewUserUseCase>()
    private val subscribeToUserSubscriptionData by inject<SubscribeToUserSubscriptionData>()

    /**
     * Supabase Client
     */
    private val supabaseClient = supabaseAuthService.supabaseClient

    /**
     * Session Status Flow
     */
    private val sessionStatus = supabaseClient.auth.sessionStatus

    /**
     * Current signed in user
     */
    private val user = sessionStatus.map { status ->
        when (status) {
            is SessionStatus.Authenticated -> {
                val authInfo = status.session.user
                authInfo?.run { checkIfIsNewUserUseCase(userInfo = authInfo) }
                getUserByIdUseCase(authInfo?.id.orEmpty()).getOrNull()
            }

            else -> null
        }
    }

    /**
     * Decompose Navigation Manager
     */
    private val navigation = StackNavigation<Configuration>()

    /**
     * Child stack responsible for initializing and managing a stack of components.
     */
    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.SignInScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    init {
        /**
         * RevenueCat Setup
         */
        coroutineScope.launch {
            Purchases.logLevel = LogLevel.DEBUG
            Purchases.configure(apiKey = getPlatform().revCatApiKey)
            subscribeToUserSubscriptionData.setupDelegate()

            user.filterNotNull().distinctUntilChanged().collect { collectedUser ->
                Purchases.sharedInstance.logIn(
                    newAppUserID = collectedUser.id,
                    onError = {},
                    onSuccess = { customerInfo: CustomerInfo, b: Boolean -> }
                )
            }
        }
    }

    /**
     * UI representation of auth methods
     */
    private fun signIn() {
        navigation.replaceCurrent(Configuration.HomeScreen)
    }

    private fun signOut() {
        navigation.replaceAll(Configuration.SignInScreen)
    }

    /**
     * Logic to navigate to each screen
     */
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
                    user = user,
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
                    user = user,
                    bingoType = configuration.bingoType,
                    onPopBack = { navigation.pop() },
                    onJoinRoom = { config -> navigation.replaceCurrent(configuration = config) },
                    onCreateRoom = {
                        navigation.pushNew(
                            Configuration.CreateScreen(
                                configuration.bingoType
                            )
                        )
                    },
                    onNavigate = { navigation.pushNew(it) }
                )
            )

            is Configuration.PlayScreen -> Child.PlayScreen(
                PlayScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                    roomId = configuration.roomId,
                    user = user,
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
                    user = user,
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
                    user = user,
                    roomId = configuration.roomId,
                    onPopBack = { navigation.pop() },
                )
            )

            Configuration.PaywallScreen -> Child.PaywallScreen(
                PaywallScreenViewModel(
                    componentContext = context,
                    onDismiss = { navigation.pop() },
                )
            )
        }
    }
}