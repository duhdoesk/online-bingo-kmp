package ui.presentation.create_user.state

import androidx.compose.ui.text.intl.Locale
import domain.user.use_case.ProfilePictures
import io.github.jan.supabase.gotrue.user.UserInfo

data class CreateUserState(
    val processing: Boolean,
    val mode: CreateUserStateMode,
    val name: String,
    val message: String,
    val pictureUri: String,
    val profilePictures: ProfilePictures?,
    val userInfo: UserInfo?
) {
    companion object {
        val IDLE = CreateUserState(
            processing = true,
            mode = CreateUserStateMode.GENERAL,
            name = "",
            message = getLocalizedMessage(),
            pictureUri = getRandomPictureUri(),
            profilePictures = null,
            userInfo = null,
        )
    }
}

enum class CreateUserStateMode {
    GENERAL, PICTURE
}

private fun getLocalizedName(): String {
    return if (Locale.current.language.contains("pt")) "Amigo Temático"
    else "Bingo Friend"
}

private fun getLocalizedMessage(): String {
    return if (Locale.current.language.contains("pt")) "O Bingo Temático é demais!"
    else "Themed Bingo is awesome!"
}

private fun getRandomPictureUri(): String {
    val possibilities = listOf(
        "https://i.imgur.com/LGISH8Q.jpg", //hypo
        "https://i.imgur.com/zkYnGof.jpg", //koala
        "https://i.imgur.com/hbEhtXV.jpg", //jellyfish
        "https://i.imgur.com/K71gi6P.jpg", //fox
        "https://i.imgur.com/OzLeMBg.jpg", //penguin
        "https://i.imgur.com/c5H2Pqx.jpg", //lion
        "https://i.imgur.com/x1FA3r1.jpg", //flamingo
        "https://i.imgur.com/DujcYDE.jpg", //tigress
        "https://i.imgur.com/2W2zDVt.jpg", //owl
        "https://i.imgur.com/3iHYtJJ.jpg", //horse
    )

    return possibilities.shuffled().first()
}