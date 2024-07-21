package ui.presentation.profile.event

sealed class ProfileScreenEvent {
    data object PopBack: ProfileScreenEvent()
    data object SignOut: ProfileScreenEvent()
    data object DeleteAccount: ProfileScreenEvent()
    data object UpdatePicture: ProfileScreenEvent()
    data object UpdatePassword: ProfileScreenEvent()
    data object UpdateName: ProfileScreenEvent()
    data object UpdateVictoryMessage: ProfileScreenEvent()
}