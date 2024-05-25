package data.theme.model

import domain.theme.model.BingoTheme
import kotlinx.serialization.Serializable

@Serializable
class BingoThemeDTO(
    val id: String,
    val name: String,
    val picture: String
) {
    fun toModel() =
        BingoTheme(id = id, name = name, pictureUri = picture)
}