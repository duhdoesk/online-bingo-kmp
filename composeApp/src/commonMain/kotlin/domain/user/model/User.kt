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
    val victoryMessage: String
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
            victoryMessage = victoryMessage
        )

}