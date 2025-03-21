package data.room.model

import dev.gitlive.firebase.firestore.DocumentSnapshot
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
    val drawnCharactersIds: List<String>,
    val state: String,
    val players: List<String>,
    val winners: List<String>
)

fun bingoRoomDTOFromDocumentSnapshot(documentSnapshot: DocumentSnapshot): BingoRoomDTO =
    BingoRoomDTO(
        id = documentSnapshot.id,
        hostId = documentSnapshot.get("hostId"),
        type = documentSnapshot.get("type"),
        name = documentSnapshot.get("name"),
        themeId = documentSnapshot.get("themeId"),
        maxWinners = documentSnapshot.get("maxWinners"),
        locked = documentSnapshot.get("locked"),
        password = documentSnapshot.get("password"),
        drawnCharactersIds = documentSnapshot.get("drawnCharactersIds")
            ?: emptyList(),
        state = documentSnapshot.get("state"),
        winners = documentSnapshot.get("winners") ?: emptyList(),
        players = documentSnapshot.get("players") ?: emptyList()
    )
