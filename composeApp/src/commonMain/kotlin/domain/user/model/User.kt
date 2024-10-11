package domain.user.model

import data.user.model.UserDTO
import dev.gitlive.firebase.firestore.Timestamp

class User(
    val id: String,
    val email: String,
    val name: String,
    val nameLastUpdated: Timestamp,
    val pictureUri: String,
    val pictureUriLastUpdated: Timestamp,
    val lastWinTimestamp: Timestamp,
    val victoryMessage: String,
    val victoryMessageLastUpdated: Timestamp
) {
    fun toDTO(): UserDTO =
        UserDTO(
            id = id,
            email = email,
            name = name,
            nameLastUpdated = nameLastUpdated,
            pictureUri = pictureUri,
            pictureUriLastUpdated = pictureUriLastUpdated,
            lastWinTimestamp = lastWinTimestamp,
            victoryMessage = victoryMessage,
            victoryMessageLastUpdated = victoryMessageLastUpdated,
        )

}

fun mockUser() = User(
    id = "",
    email = "",
    name = "",
    nameLastUpdated = Timestamp.now(),
    pictureUri = "",
    pictureUriLastUpdated = Timestamp.now(),
    lastWinTimestamp = Timestamp.now(),
    victoryMessage = "",
    victoryMessageLastUpdated = Timestamp.now(),
)