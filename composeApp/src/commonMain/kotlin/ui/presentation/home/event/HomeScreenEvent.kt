package ui.presentation.home.event

import ui.navigation.Configuration

sealed class HomeScreenEvent {
    data object UILoaded: HomeScreenEvent()
    data class Navigate(val configuration: Configuration): HomeScreenEvent()
}