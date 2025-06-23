package data.room.model

import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable

@Serializable
class BingoRoomDTO(
    val id: String,
    val hostId: String,
    val type: String,
    val name: String,
    val themeId: String?,
    val maxWinners: Int,
    val locked: Boolean,
    val password: String?,
    val raffled: List<String>,
    val state: String,
    val players: List<String>,
    val winners: List<String>,
    val createdAt: Timestamp
)

fun bingoRoomDTOFromDocumentSnapshot(documentSnapshot: DocumentSnapshot): BingoRoomDTO =
    BingoRoomDTO(
        id = documentSnapshot.id,
        hostId = documentSnapshot.get("hostId"),
        type = documentSnapshot.get("type") ?: "CLASSIC",
        name = documentSnapshot.get("name"),
        themeId = documentSnapshot.get("themeId"),
        maxWinners = documentSnapshot.get("maxWinners") ?: 1,
        locked = documentSnapshot.get("locked"),
        password = documentSnapshot.get("password"),
        raffled = documentSnapshot.get("drawnCharactersIds") ?: emptyList(),
        state = documentSnapshot.get("state") ?: "FINISHED",
        winners = documentSnapshot.get("winners") ?: emptyList(),
        players = documentSnapshot.get("players") ?: emptyList(),
        createdAt = documentSnapshot.get("createdAt") ?: Timestamp.now()
    )
