package data.user.model

import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.Timestamp
import domain.user.model.User

data class UserDTO(
    val id: String,
    val email: String?,
    val name: String?,
    val nameLastUpdated: Timestamp?,
    val pictureUri: String?,
    val pictureUriLastUpdated: Timestamp?,
    val lastWinTimestamp: Timestamp?,
    val victoryMessage: String?,
    val victoryMessageLastUpdated: Timestamp?
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

fun DocumentSnapshot.toUserDTO(): UserDTO {
    return UserDTO(
        id = id,
        name = this.get("name"),
        email = this.get("email"),
        pictureUri = this.get("pictureUri"),
        nameLastUpdated = this.get("nameLastUpdated"),
        pictureUriLastUpdated = this.get("pictureUriLastUpdated"),
        lastWinTimestamp = this.get("lastWinTimestamp"),
        victoryMessage = this.get("victoryMessage"),
        victoryMessageLastUpdated = this.get("victoryMessageLastUpdated")
    )
}
