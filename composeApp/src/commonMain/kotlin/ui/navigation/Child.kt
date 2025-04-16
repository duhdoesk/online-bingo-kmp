package ui.navigation

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

sealed class Child {
    data class ChangePasswordScreen(val component: ChangePasswordScreenComponent) : Child()
    data class CreateScreen(val component: CreateRoomScreenComponent) : Child()
    data class CreateUserScreen(val component: CreateUserViewModel) : Child()
    data class ForgotPasswordScreen(val component: ForgotPasswordScreenComponent) : Child()
    data class HomeScreen(val component: HomeScreenComponent) : Child()
    data class HostScreen(val component: RoomHostViewModel) : Child()
    data class JoinScreen(val component: JoinScreenComponent) : Child()
    data class PaywallScreen(val component: PaywallScreenViewModel) : Child()
    data class PlayerScreen(val component: RoomPlayerViewModel) : Child()
    data class ProfileScreen(val component: ProfileScreenComponent) : Child()
    data class SignInScreen(val component: SignInScreenComponent) : Child()
    data class SplashScreen(val component: SplashScreenComponent) : Child()
    data class ThemesScreen(val component: ThemesScreenComponent) : Child()
}
