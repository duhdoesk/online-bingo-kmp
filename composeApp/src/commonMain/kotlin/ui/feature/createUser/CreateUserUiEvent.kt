package ui.feature.createUser

sealed class CreateUserUiEvent {
    data object CreateUser : CreateUserUiEvent()
    data object SignOut : CreateUserUiEvent()
    data class UpdateName(val name: String) : CreateUserUiEvent()
    data class UpdateVictoryMessage(val message: String) : CreateUserUiEvent()
    data class UpdatePicture(val pictureUri: String) : CreateUserUiEvent()
}
