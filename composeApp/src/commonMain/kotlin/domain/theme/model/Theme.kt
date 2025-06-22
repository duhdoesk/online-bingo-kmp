package domain.theme.model

data class Theme(
    val id: String,
    val name: String,
    val pictureUri: String
)

fun mockBingoThemeList(): List<Theme> {
    return listOf(
        Theme(
            id = "a",
            name = "Numeric",
            pictureUri = "https://cdn.teachercreated.com/20240118/covers/900sqp/9123.png"
        ),
        Theme(
            id = "b",
            name = "Bichos",
            pictureUri = "https://i.imgur.com/Yr9zXxb.jpg"
        ),
        Theme(
            id = "c",
            name = "Flores",
            pictureUri = "https://i.imgur.com/uBThWgI.jpg"
        ),
        Theme(
            id = "d",
            name = "Profissões",
            pictureUri = "https://i.imgur.com/oOC9Z9P.jpg"
        )
    )
}
