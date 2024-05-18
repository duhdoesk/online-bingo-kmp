package data.theme.model

import domain.theme.model.BingoTheme

class BingoThemeDTO(
    val id: String,
    val name: String,
    val pictureUri: String
) {
    fun toModel() =
        BingoTheme(id = id, name = name, pictureUri = pictureUri)
}