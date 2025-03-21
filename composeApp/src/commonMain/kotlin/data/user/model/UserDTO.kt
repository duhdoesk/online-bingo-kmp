package data.user.model

import dev.gitlive.firebase.firestore.Timestamp
import domain.user.model.User

class UserDTO(
    private val id: String,
    private val email: String?,
    private val name: String?,
    private val nameLastUpdated: Timestamp?,
    private val pictureUri: String?,
    private val pictureUriLastUpdated: Timestamp?,
    private val lastWinTimestamp: Timestamp?,
    private val victoryMessage: String?,
    private val victoryMessageLastUpdated: Timestamp?
) {
    fun toModel(): User =
        User(
            id = id,
            name = name ?: "Bingo Friend",
            email = email ?: "",
            pictureUri = pictureUri ?: "",
            nameLastUpdated = nameLastUpdated ?: Timestamp(0, 0),
            pictureUriLastUpdated = pictureUriLastUpdated ?: Timestamp(0, 0),
            lastWinTimestamp = lastWinTimestamp ?: Timestamp(0, 0),
            victoryMessage = victoryMessage ?: "",
            victoryMessageLastUpdated = victoryMessageLastUpdated ?: Timestamp(0, 0)
        )
}
