package data.character.model

import domain.character.model.Character

class CharacterDTO(
    val id: String,
    val name: String,
    val pictureUri: String
) {
    fun toModel(): Character =
        Character(id = id, name = name, pictureUri = pictureUri)
}