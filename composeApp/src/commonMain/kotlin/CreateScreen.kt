import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.navigation.Child
import ui.presentation.change_password.ChangePasswordScreen
import ui.presentation.create_room.CreateRoomScreen
import ui.presentation.create_user.CreateUserScreen
import ui.presentation.forgot_password.ForgotPasswordScreen
import ui.presentation.home.HomeScreen
import ui.presentation.paywall.PaywallScreen
import ui.presentation.join_room.JoinScreen
import ui.presentation.profile.ProfileScreen
import ui.presentation.profile.picture.EditProfilePictureScreen
import ui.presentation.room.screens.host.HostScreen
import ui.presentation.room.screens.player.PlayerScreen
import ui.presentation.sign_in.SignInScreen
import ui.presentation.sign_up.SignUpScreen
import ui.presentation.themes.ThemesScreen
import ui.presentation.util.WindowInfo

@ExperimentalResourceApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    instance: Child,
    windowInfo: WindowInfo,
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
            viewModel = instance.component,
        )

        is Child.PlayerScreen -> PlayerScreen(
            viewModel = instance.component,
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
            component = instance.component,
        )

        is Child.SignUpScreen -> SignUpScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.ForgotPasswordScreen -> ForgotPasswordScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.EditProfilePictureScreen -> EditProfilePictureScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.ChangePasswordScreen -> ChangePasswordScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.PaywallScreen -> PaywallScreen(
            viewModel = instance.component,
        )

        is Child.CreateUserScreen -> CreateUserScreen(
            viewModel = instance.component
        )
    }
}