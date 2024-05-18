package domain.theme.model

data class BingoTheme(
    val id: String,
    val name: String,
    val pictureUri: String
)

fun mockBingoThemeList(): List<BingoTheme> {
    return listOf(
        BingoTheme(
            id = "a",
            name = "Numeric",
            pictureUri = "https://cdn.teachercreated.com/20240118/covers/900sqp/9123.png"
        ),
        BingoTheme(
            id = "b",
            name = "Bichos",
            pictureUri = "https://i.imgur.com/Yr9zXxb.jpg"
        ),
        BingoTheme(
            id = "c",
            name = "Flores",
            pictureUri = "https://i.imgur.com/uBThWgI.jpg"
        ),
        BingoTheme(
            id = "d",
            name = "Profissões",
            pictureUri = "https://i.imgur.com/oOC9Z9P.jpg"
        )
    )
}
