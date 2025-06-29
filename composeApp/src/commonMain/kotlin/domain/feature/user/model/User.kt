package domain.feature.user.model

import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.LocalDateTime

data class User(
    val id: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val email: String,
    val name: String,
    val victoryMessage: String,
    val pictureUri: String,
    val tier: Tier
)

fun getLocalizedName(): String {
    return when (Locale.current.language) {
        "es" -> "Amigo Temático"
        "pt" -> "Amigo Temático"
        else -> "Bingo Friend"
    }
}

fun getLocalizedMessage(): String {
    return when (Locale.current.language) {
        "es" -> "¡El Bingo Temático es increíble!"
        "pt" -> "O Bingo Temático é demais"
        else -> "Themed Bingo is awesome!"
    }
}

fun getRandomPictureUri(): String {
    val possibilities = listOf(
        "https://i.imgur.com/LGISH8Q.jpg", // hypo
        "https://i.imgur.com/zkYnGof.jpg", // koala
        "https://i.imgur.com/hbEhtXV.jpg", // jellyfish
        "https://i.imgur.com/K71gi6P.jpg", // fox
        "https://i.imgur.com/OzLeMBg.jpg", // penguin
        "https://i.imgur.com/c5H2Pqx.jpg", // lion
        "https://i.imgur.com/x1FA3r1.jpg", // flamingo
        "https://i.imgur.com/DujcYDE.jpg", // tigress
        "https://i.imgur.com/2W2zDVt.jpg", // owl
        "https://i.imgur.com/3iHYtJJ.jpg" // horse
    )

    return possibilities.shuffled().first()
}

fun mockUser(): User {
    return User(
        id = "123456789",
        email = "usuario@mockado.com.br",
        createdAt = LocalDateTime(2023, 1, 1, 0, 0),
        updatedAt = LocalDateTime(2023, 1, 1, 0, 0),
        name = "Usuário Mockado",
        victoryMessage = "O Bingo temático é demais!",
        pictureUri = "",
        tier = Tier.FREE
    )
}
