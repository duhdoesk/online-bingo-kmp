package ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.feature.createRoom.CreateRoomScreen
import ui.feature.createUser.CreateUserScreen
import ui.feature.home.HomeScreen
import ui.feature.joinRoom.JoinScreen
import ui.feature.paywall.PaywallScreen
import ui.feature.profile.ProfileScreen
import ui.feature.room.screens.host.HostScreen
import ui.feature.room.screens.player.PlayerScreen
import ui.feature.signIn.SignInScreen
import ui.feature.splash.SplashScreen
import ui.feature.themes.ThemesScreen
import ui.util.WindowInfo

@ExperimentalResourceApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    instance: Child,
    windowInfo: WindowInfo
) {
    when (instance) {
        is Child.HomeScreen -> HomeScreen(
            component = instance.component
        )

        is Child.ThemesScreen -> ThemesScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.CreateScreen -> CreateRoomScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.HostScreen -> HostScreen(
            viewModel = instance.component
        )

        is Child.PlayerScreen -> PlayerScreen(
            viewModel = instance.component
        )

        is Child.JoinScreen -> JoinScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.ProfileScreen -> ProfileScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.SignInScreen -> SignInScreen(
            component = instance.component
        )

        is Child.PaywallScreen -> PaywallScreen(
            viewModel = instance.component
        )

        is Child.CreateUserScreen -> CreateUserScreen(
            viewModel = instance.component
        )

        is Child.SplashScreen -> SplashScreen(
            component = instance.component
        )
    }
}
