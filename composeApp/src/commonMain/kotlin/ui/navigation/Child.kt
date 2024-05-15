package ui.navigation

import ui.presentation.create_room.CreateScreenComponent
import ui.presentation.home.HomeScreenComponent
import ui.presentation.host.HostScreenComponent
import ui.presentation.join_room.JoinScreenComponent
import ui.presentation.play.PlayScreenComponent
import ui.presentation.profile.ProfileScreenComponent
import ui.presentation.themes.ThemesScreenComponent

sealed class Child {
    data class HomeScreen(val component: HomeScreenComponent): Child()
    data class ThemesScreen(val component: ThemesScreenComponent): Child()
    data class CreateScreen(val component: CreateScreenComponent): Child()
    data class JoinScreen(val component: JoinScreenComponent): Child()
    data class HostScreen(val component: HostScreenComponent): Child()
    data class PlayScreen(val component: PlayScreenComponent): Child()
    data class ProfileScreen(val component: ProfileScreenComponent): Child()
}