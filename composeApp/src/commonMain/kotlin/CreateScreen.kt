import androidx.compose.runtime.Composable
import ui.navigation.Child
import ui.presentation.change_password.ChangePasswordScreen
import ui.presentation.create_room.CreateRoomScreen
import ui.presentation.forgot_password.ForgotPasswordScreen
import ui.presentation.home.HomeScreen
import ui.presentation.join_room.JoinScreen
import ui.presentation.profile.ProfileScreen
import ui.presentation.profile.picture.EditProfilePictureScreen
import ui.presentation.room.classic.host.ClassicHostScreen
import ui.presentation.room.classic.play.ClassicPlayScreen
import ui.presentation.room.themed.host.HostScreen
import ui.presentation.room.themed.play.PlayScreen
import ui.presentation.sign_in.SignInScreen
import ui.presentation.sign_up.SignUpScreen
import ui.presentation.themes.ThemesScreen
import ui.presentation.util.WindowInfo

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
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.JoinScreen -> JoinScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.PlayScreen -> PlayScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.ProfileScreen -> ProfileScreen(
            component = instance.component,
            windowInfo = windowInfo
        )

        is Child.SignInScreen -> SignInScreen(
            component = instance.component,
            windowInfo = windowInfo
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

        is Child.ClassicHostScreen -> ClassicHostScreen(
            component = instance.component,
        )

        is Child.ClassicPlayScreen -> ClassicPlayScreen(
            component = instance.component,
        )
    }
}