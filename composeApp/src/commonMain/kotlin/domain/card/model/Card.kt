package domain.card.model

import domain.character.model.Character

data class Card(
    val userId: String,
    val characters: List<String>
)