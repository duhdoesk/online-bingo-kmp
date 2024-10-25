package data.theme.model

import androidx.compose.ui.text.intl.Locale
import domain.theme.model.BingoTheme
import kotlinx.serialization.Serializable

@Serializable
class BingoThemeDTO(
    val id: String,
    val name: String,
    val nameEnglish: String,
    val picture: String
) {
    fun toModel(): BingoTheme {
        val localization = Locale.current.language
        val localizedName = if (localization.contains("pt")) name else nameEnglish
        return BingoTheme(id = id, name = localizedName, pictureUri = picture)
    }
}