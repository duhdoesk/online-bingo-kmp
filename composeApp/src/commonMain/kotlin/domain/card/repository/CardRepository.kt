package domain.card.repository

import domain.card.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    fun flowCardByRoomAndUserID(roomId: String, userId: String): Flow<Card?>

    suspend fun getCardByRoomAndUserID(roomId: String, userId: String): Result<Card>

    suspend fun addCardByRoomAndUserID(
        roomId: String,
        userId: String,
        charactersIDs: List<String>
    ): Result<Unit>

    suspend fun updateCardByRoomAndUserID(
        roomId: String,
        userId: String,
        charactersIDs: List<String>
    ): Result<Unit>
}
