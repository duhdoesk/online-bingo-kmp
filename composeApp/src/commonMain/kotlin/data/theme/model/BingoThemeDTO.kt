package data.theme.model

import androidx.compose.ui.text.intl.Locale
import domain.theme.model.Theme
import kotlinx.serialization.Serializable

@Serializable
class BingoThemeDTO(
    val id: String,
    val name: String,
    val nameEnglish: String,
    val nameSpanish: String?,
    val picture: String
) {
    fun toModel(): Theme {
        val localization = Locale.current.language

        val localizedName =
            if (localization.contains("pt")) {
                name
            } else if (localization.contains("es")) {
                nameSpanish ?: nameEnglish
            } else {
                nameEnglish
            }

        return Theme(id = id, name = localizedName, pictureUri = picture)
    }
}
