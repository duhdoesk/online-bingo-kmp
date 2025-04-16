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
import domain.user.useCase.GetUserByIdUseCase
import domain.user.useCase.ObserveUserUseCase
import getPlatform
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.navigation.Child.ChangePasswordScreen
import ui.navigation.Child.CreateUserScreen
import ui.navigation.Child.ForgotPasswordScreen
import ui.navigation.Child.HomeScreen
import ui.navigation.Child.HostScreen
import ui.navigation.Child.JoinScreen
import ui.navigation.Child.PaywallScreen
import ui.navigation.Child.PlayerScreen
import ui.navigation.Child.ProfileScreen
import ui.navigation.Child.SignInScreen
import ui.navigation.Child.SplashScreen
import ui.navigation.Child.ThemesScreen
import ui.presentation.changePassword.ChangePasswordScreenComponent
import ui.presentation.createRoom.CreateRoomScreenComponent
import ui.presentation.createUser.CreateUserViewModel
import ui.presentation.forgotPassword.ForgotPasswordScreenComponent
import ui.presentation.home.HomeScreenComponent
import ui.presentation.joinRoom.JoinScreenComponent
import ui.presentation.paywall.PaywallScreenViewModel
import ui.presentation.profile.ProfileScreenComponent
import ui.presentation.room.RoomHostViewModel
import ui.presentation.room.RoomPlayerViewModel
import ui.presentation.signIn.SignInScreenComponent
import ui.presentation.splash.SplashScreenComponent
import ui.presentation.themes.ThemesScreenComponent
import util.componentCoroutineScope

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentContext.componentCoroutineScope()

    /** Use Cases */
    private val supabaseAuthService by inject<SupabaseAuthService>()
    private val getUserByIdUseCase by inject<GetUserByIdUseCase>()
    private val subscribeToUserSubscriptionData by inject<SubscribeToUserSubscriptionData>()
    private val observeUserUseCase by inject<ObserveUserUseCase>()

    /** Supabase Client */
    private val _supabaseClient = supabaseAuthService.supabaseClient

    /** Session Status Flow */
    private val _sessionStatus = _supabaseClient.auth.sessionStatus

    /** Current signed in user */
    private val _user = _sessionStatus.map { status ->
        when (status) {
            is SessionStatus.Authenticated -> {
                val authInfo = status.session.user
                getUserByIdUseCase(authInfo?.id.orEmpty()).getOrNull()
            }

            else -> null
        }
    }

    /** Decompose Navigation Manager */
    private val navigation = StackNavigation<Configuration>()

    /** Child stack responsible for initializing and managing a stack of components. */
    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.SplashScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    init {
        /** RevenueCat Setup */
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

    /** Globally handles navigation for Sign In and Sign Out authentication methods */
    private fun navigateAfterSignIn(userId: String?) {
        coroutineScope.launch {
            userId?.let {
                observeUserUseCase(it).collect { user ->
                    if (user == null) {
                        navigation.replaceCurrent(Configuration.CreateUserScreen)
                    } else {
                        navigation.replaceCurrent(Configuration.HomeScreen)
                    }
                }
            }
        }
    }

    private fun navigateAfterSignOut() {
        navigation.replaceAll(Configuration.SignInScreen)
    }

    /** Nav host */
    private fun createChild(
        configuration: Configuration,
        context: ComponentContext
    ): Child {
        return when (configuration) {
            Configuration.HomeScreen -> HomeScreen(
                HomeScreenComponent(
                    componentContext = context,
                    onNavigate = { config -> navigation.pushNew(configuration = config) },
                    onUserNotAuthenticated = { navigation.replaceAll(Configuration.SignInScreen) },
                    onUserNotCreated = { navigation.replaceAll(Configuration.CreateUserScreen) }
                )
            )

            Configuration.ThemesScreen -> ThemesScreen(
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

            is Configuration.HostScreenThemed -> HostScreen(
                RoomHostViewModel(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                    roomId = configuration.roomId
                )
            )

            is Configuration.HostScreenClassic -> HostScreen(
                RoomHostViewModel(
                    componentContext = context,
                    roomId = configuration.roomId,
                    onPopBack = { navigation.pop() }
                )
            )

            is Configuration.PlayerScreenThemed -> PlayerScreen(
                RoomPlayerViewModel(
                    componentContext = context,
                    onPopBack = { navigation.pop() },
                    roomId = configuration.roomId,
                    user = _user
                )
            )

            is Configuration.PlayerScreenClassic -> PlayerScreen(
                RoomPlayerViewModel(
                    componentContext = context,
                    roomId = configuration.roomId,
                    onPopBack = { navigation.pop() },
                    user = _user
                )
            )

            is Configuration.JoinScreen -> JoinScreen(
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

            Configuration.ProfileScreen -> ProfileScreen(
                ProfileScreenComponent(
                    componentContext = context,
                    user = _user,
                    onPopBack = { navigation.pop() },
                    onSignOut = { navigateAfterSignOut() }
                )
            )

            Configuration.SignInScreen -> SignInScreen(
                SignInScreenComponent(
                    componentContext = context,
                    onSignIn = { userId -> navigateAfterSignIn(userId) }
                )
            )

            Configuration.ForgotPasswordScreen -> ForgotPasswordScreen(
                ForgotPasswordScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() }
                )
            )

            Configuration.ChangePasswordScreen -> ChangePasswordScreen(
                ChangePasswordScreenComponent(
                    componentContext = context,
                    onPopBack = { navigation.pop() }
                )
            )

            Configuration.PaywallScreen -> PaywallScreen(
                PaywallScreenViewModel(
                    componentContext = context,
                    onDismiss = { navigation.pop() }
                )
            )

            is Configuration.CreateUserScreen -> CreateUserScreen(
                CreateUserViewModel(
                    componentContext = context,
                    onSignOut = { navigation.replaceAll(Configuration.SignInScreen) },
                    onUserCreated = { navigation.pushNew(Configuration.HomeScreen) }
                )
            )

            is Configuration.SplashScreen -> SplashScreen(
                SplashScreenComponent(
                    componentContext = context,
                    onSignedIn = { userId -> navigateAfterSignIn(userId) },
                    onNotSignedIn = { navigation.replaceAll(Configuration.SignInScreen) }
                )
            )
        }
    }
}
