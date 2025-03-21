package data.card.model

import domain.card.model.Card

class CardDTO(
    val userId: String,
    val characters: List<String>?
) {
    fun toModel(): Card =
        Card(userId = userId, characters = characters)
}
