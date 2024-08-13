package domain.card.repository

import domain.card.model.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {

    fun flowCardByRoomAndUserID(roomId: String, userId: String): Flow<Card>

    suspend fun setCardByRoomAndUserID(
        roomId: String,
        userId: String,
        charactersIDs: List<String>
    ): Result<Unit>
}