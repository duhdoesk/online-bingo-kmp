package data.user.model

import domain.user.model.User

class UserDTO(
    val id: String,
    val name: String,
    val pictureUri: String
) {
    fun toModel(): User =
        User(id = id, name = name, pictureUri = pictureUri)
}