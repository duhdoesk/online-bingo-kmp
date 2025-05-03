@file:OptIn(ExperimentalCoroutinesApi::class)

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
import domain.billing.SubscribeToUserSubscriptionData
import domain.user.useCase.GetSignedInUserUseCase
import domain.util.resource.Resource
import getPlatform
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.feature.createRoom.CreateRoomScreenComponent
import ui.feature.createUser.CreateUserComponent
import ui.feature.home.HomeScreenComponent
import ui.feature.joinRoom.JoinScreenComponent
import ui.feature.paywall.PaywallScreenViewModel
import ui.feature.profile.ProfileScreenComponent
import ui.feature.room.RoomHostViewModel
import ui.feature.room.RoomPlayerViewModel
import ui.feature.signIn.SignInScreenComponent
import ui.feature.splash.SplashScreenComponent
import ui.feature.themes.ThemesScreenComponent
import ui.navigation.Child.CreateUserScreen
import ui.navigation.Child.HomeScreen
import ui.navigation.Child.HostScreen
import ui.navigation.Child.JoinScreen
import ui.navigation.Child.PaywallScreen
import ui.navigation.Child.PlayerScreen
import ui.navigation.Child.ProfileScreen
import ui.navigation.Child.SignInScreen
import ui.navigation.Child.SplashScreen
import ui.navigation.Child.ThemesScreen
import util.componentCoroutineScope

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentContext.componentCoroutineScope()

    /** Use Cases */
    private val getSignedInUserUseCase by inject<GetSignedInUserUseCase>()
    private val subscribeToUserSubscriptionData by inject<SubscribeToUserSubscriptionData>()
    private val getSignedInUser by inject<GetSignedInUserUseCase>()

    /** Current signed in user */
    private val _user = getSignedInUserUseCase()

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
        coroutineScope.launch {
            /** RevenueCat Setup */
            Purchases.logLevel = LogLevel.DEBUG
            Purchases.configure(apiKey = getPlatform().revCatApiKey)
            subscribeToUserSubscriptionData.setupDelegate()

            _user.filterNotNull().distinctUntilChanged().collect { resource ->
                if (resource is Resource.Success) {
                    Purchases.sharedInstance.logIn(
                        newAppUserID = resource.data.id,
                        onError = {},
                        onSuccess = { customerInfo: CustomerInfo, b: Boolean -> }
                    )
                }
            }
        }
    }

    /** Globally handles navigation for Sign In and Sign Out authentication methods */
    private fun navigateAfterSignIn() {
        coroutineScope.launch {
            getSignedInUser().collect { user ->
                if (user == null) {
                    navigation.replaceCurrent(Configuration.CreateUserScreen)
                } else {
                    navigation.replaceCurrent(Configuration.HomeScreen)
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
                    roomId = configuration.roomId
                )
            )

            is Configuration.PlayerScreenClassic -> PlayerScreen(
                RoomPlayerViewModel(
                    componentContext = context,
                    roomId = configuration.roomId,
                    onPopBack = { navigation.pop() }
                )
            )

            is Configuration.JoinScreen -> JoinScreen(
                JoinScreenComponent(
                    componentContext = context,
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
                    onPopBack = { navigation.pop() },
                    onSignOut = { navigateAfterSignOut() }
                )
            )

            Configuration.SignInScreen -> SignInScreen(
                SignInScreenComponent(
                    componentContext = context,
                    onSignIn = { userId -> navigateAfterSignIn() }
                )
            )

            Configuration.PaywallScreen -> PaywallScreen(
                PaywallScreenViewModel(
                    componentContext = context,
                    onDismiss = { navigation.pop() }
                )
            )

            is Configuration.CreateUserScreen -> CreateUserScreen(
                CreateUserComponent(
                    componentContext = context,
                    onSignOut = { navigation.replaceAll(Configuration.SignInScreen) },
                    onUserCreated = { navigation.pushNew(Configuration.HomeScreen) }
                )
            )

            is Configuration.SplashScreen -> SplashScreen(
                SplashScreenComponent(
                    componentContext = context,
                    onSignedIn = { navigateAfterSignIn() },
                    onNotSignedIn = { navigation.replaceAll(Configuration.SignInScreen) }
                )
            )
        }
    }
}
