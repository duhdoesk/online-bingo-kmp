package ui.presentation.home.event

import ui.navigation.Configuration

sealed class HomeScreenEvent {
    data class Navigate(val configuration: Configuration): HomeScreenEvent()
}