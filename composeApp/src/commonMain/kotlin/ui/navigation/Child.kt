package ui.navigation

import ui.presentation.home.HomeScreenComponent
import ui.presentation.themes.ThemesScreenComponent

sealed class Child {
    data class HomeScreen(val component: HomeScreenComponent): Child()
    data class ThemesScreen(val component: ThemesScreenComponent): Child()
}