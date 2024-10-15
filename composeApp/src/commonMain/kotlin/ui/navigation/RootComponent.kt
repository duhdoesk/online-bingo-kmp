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
import ui.presentation.create_user.CreateUserViewModel
import ui.presentation.forgot_password.ForgotPasswordScreenComponent
import ui.presentation.home.HomeScreenComponent
import ui.presentation.join_room.JoinScreenComponent
import ui.presentation.paywall.PaywallScreenViewModel
import ui.presentation.profile.ProfileScreenComponent
import ui.presentation.room.RoomHostViewModel
import ui.presentation.room.RoomPlayerViewModel
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
    private val _user = sessionStatus.map { status ->
        when (status) {
            is SessionStatus.Authenticated -> {
                val authInfo = status.session.user
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

            _user.filterNotNull().distinctUntilChanged().collect { collectedUser ->
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
        coroutineScope.launch {
            when (val status = sessionStatus.value) {
                is SessionStatus.Authenticated -> {
                    val thisUser = getUserByIdUseCase(status.session.user?.id.orEmpty()).getOrNull()

                    if (thisUser != null) navigation.replaceCurrent(Configuration.HomeScreen)
                    else navigation.replaceCurrent(Configuration.CreateUserScreen(status.session.user))
                }

                else -> {
                    return@launch
                }
            }
        }
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
                    user = _user,
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
                    user = _user,
                    bingoType = configuration.bingoType,
                    onPopBack = { navigation.pop() },
                    onCreateRoom = { receivedConfig ->
                        navigation.replaceCurrent(configuration = receivedConfig)
                    }
                )
            )

            is Configuration.HostScreenThemed -> Child.HostScreen(
                RoomHostViewModel(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                    roomId = configuration.roomId,
                    user = _user,
                )
            )

            is Configuration.HostScreenClassic -> Child.HostScreen(
                RoomHostViewModel(
                    componentContext = context,
                    roomId = configuration.roomId,
                    onPopBack = { navigation.pop() },
                    user = _user,
                )
            )

            is Configuration.PlayerScreenThemed -> Child.PlayerScreen(
                RoomPlayerViewModel(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                    roomId = configuration.roomId,
                    user = _user,
                )
            )

            is Configuration.PlayerScreenClassic -> Child.PlayerScreen(
                RoomPlayerViewModel(
                    componentContext = context,
                    roomId = configuration.roomId,
                    onPopBack = { navigation.pop() },
                    user = _user,
                )
            )

            is Configuration.JoinScreen -> Child.JoinScreen(
                JoinScreenComponent(
                    componentContext = context,
                    user = _user,
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

            Configuration.ProfileScreen -> Child.ProfileScreen(
                ProfileScreenComponent(
                    componentContext = context,
                    user = _user,
                    onPopBack = { navigation.pop() },
                    onSignOut = { signOut() },
                )
            )

            Configuration.SignInScreen -> Child.SignInScreen(
                SignInScreenComponent(
                    componentContext = context,
                    onSignIn = { signIn() },
                    supabaseClient = supabaseClient,
                    sessionStatus = sessionStatus,
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

            Configuration.ChangePasswordScreen -> Child.ChangePasswordScreen(
                ChangePasswordScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                )
            )

            Configuration.PaywallScreen -> Child.PaywallScreen(
                PaywallScreenViewModel(
                    componentContext = context,
                    onDismiss = { navigation.pop() },
                )
            )

            is Configuration.CreateUserScreen -> Child.CreateUserScreen(
                CreateUserViewModel(
                    componentContext = context,
                    sessionStatus = sessionStatus,
                    onSignOut = { navigation.replaceAll(Configuration.SignInScreen) },
                    onUserCreated = { navigation.pushNew(Configuration.HomeScreen) },
                )
            )
        }
    }
}