import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.navigation.Child
import ui.presentation.changePassword.ChangePasswordScreen
import ui.presentation.createRoom.CreateRoomScreen
import ui.presentation.createUser.CreateUserScreen
import ui.presentation.forgotPassword.ForgotPasswordScreen
import ui.presentation.home.HomeScreen
import ui.presentation.joinRoom.JoinScreen
import ui.presentation.paywall.PaywallScreen
import ui.presentation.profile.ProfileScreen
import ui.presentation.room.screens.host.HostScreen
import ui.presentation.room.screens.player.PlayerScreen
import ui.presentation.signIn.SignInScreen
import ui.presentation.signUp.SignUpScreen
import ui.presentation.themes.ThemesScreen
import ui.presentation.util.WindowInfo

@ExperimentalResourceApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    instance: Child,
    windowInfo: WindowInfo
) {
    when (instance) {
        is Child.HomeScreen -> HomeScreen(
            component = instance.component,
            windowInfo = windowInfo
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

        is Child.SignUpScreen -> SignUpScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.ForgotPasswordScreen -> ForgotPasswordScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.ChangePasswordScreen -> ChangePasswordScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.PaywallScreen -> PaywallScreen(
            viewModel = instance.component
        )

        is Child.CreateUserScreen -> CreateUserScreen(
            viewModel = instance.component
        )
    }
}
