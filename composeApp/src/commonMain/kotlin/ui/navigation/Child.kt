package ui.navigation

import ui.presentation.change_password.ChangePasswordScreenComponent
import ui.presentation.create_room.CreateRoomScreenComponent
import ui.presentation.forgot_password.ForgotPasswordScreenComponent
import ui.presentation.home.HomeScreenComponent
import ui.presentation.room.themed.host.HostScreenComponent
import ui.presentation.join_room.JoinScreenComponent
import ui.presentation.room.themed.play.PlayScreenComponent
import ui.presentation.profile.ProfileScreenComponent
import ui.presentation.profile.picture.EditProfilePictureScreenComponent
import ui.presentation.room.classic.host.ClassicHostScreenComponent
import ui.presentation.room.classic.play.ClassicPlayScreenComponent
import ui.presentation.sign_in.SignInScreenComponent
import ui.presentation.sign_up.SignUpScreenComponent
import ui.presentation.themes.ThemesScreenComponent

sealed class Child {
    data class HomeScreen(val component: HomeScreenComponent): Child()
    data class ThemesScreen(val component: ThemesScreenComponent): Child()
    data class CreateScreen(val component: CreateRoomScreenComponent): Child()
    data class JoinScreen(val component: JoinScreenComponent): Child()
    data class HostScreen(val component: HostScreenComponent): Child()
    data class PlayScreen(val component: PlayScreenComponent): Child()
    data class ProfileScreen(val component: ProfileScreenComponent): Child()
    data class SignInScreen(val component: SignInScreenComponent): Child()
    data class SignUpScreen(val component: SignUpScreenComponent): Child()
    data class ForgotPasswordScreen(val component: ForgotPasswordScreenComponent): Child()
    data class EditProfilePictureScreen(val component: EditProfilePictureScreenComponent): Child()
    data class ChangePasswordScreen(val component: ChangePasswordScreenComponent): Child()
    data class ClassicHostScreen(val component: ClassicHostScreenComponent): Child()
    data class ClassicPlayScreen(val component: ClassicPlayScreenComponent): Child()
}