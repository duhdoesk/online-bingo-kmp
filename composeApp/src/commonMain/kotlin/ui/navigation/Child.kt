package ui.navigation

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
import ui.feature.update.UpdateScreenComponent

sealed class Child {
    data class CreateScreen(val component: CreateRoomScreenComponent) : Child()
    data class CreateUserScreen(val component: CreateUserComponent) : Child()
    data class HomeScreen(val component: HomeScreenComponent) : Child()
    data class HostScreen(val component: RoomHostViewModel) : Child()
    data class JoinScreen(val component: JoinScreenComponent) : Child()
    data class PaywallScreen(val component: PaywallScreenViewModel) : Child()
    data class PlayerScreen(val component: RoomPlayerViewModel) : Child()
    data class ProfileScreen(val component: ProfileScreenComponent) : Child()
    data class SignInScreen(val component: SignInScreenComponent) : Child()
    data class SplashScreen(val component: SplashScreenComponent) : Child()
    data class ThemesScreen(val component: ThemesScreenComponent) : Child()
    data class UpdateScreen(val component: UpdateScreenComponent) : Child()
}
