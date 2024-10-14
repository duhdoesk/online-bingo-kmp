package ui.presentation.create_user.event

sealed class CreateUserEvent {
    data object CreateUser: CreateUserEvent()
    data object SignOut: CreateUserEvent()
    data object UiLoaded: CreateUserEvent()
    data class UpdateName(val name: String): CreateUserEvent()
    data class UpdateVictoryMessage(val message: String): CreateUserEvent()
    data class UpdatePicture(val pictureUri: String): CreateUserEvent()
}