package data.character.model

import androidx.compose.ui.text.intl.Locale
import domain.character.model.Character

class CharacterDTO(
    val id: String,
    val name: String,
    val nameInEnglish: String?,
    val nameInSpanish: String?,
    val pictureUri: String
) {
    fun toModel(): Character {
        val localization = Locale.current.language

        val localizedName =
            if (localization.contains("pt")) {
                name
            } else if (localization.contains("es")) {
                nameInSpanish ?: name
            } else {
                nameInEnglish ?: name
            }

        return Character(id = id, name = localizedName, pictureUri = pictureUri)
    }
}
