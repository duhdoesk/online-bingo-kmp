package data.card.repository

import data.card.model.CardDTO
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.card.model.Card
import domain.card.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CardRepositoryImpl(
    firestore: FirebaseFirestore
) : CardRepository {

    private val rootCollection = firestore.collection("rooms")

    override fun flowCardByRoomAndUserID(roomId: String, userId: String): Flow<Card> {
        return rootCollection
            .document(roomId)
            .collection("cards")
            .document(userId)
            .snapshots
            .map { documentSnapshot ->
                CardDTO(
                    userId = documentSnapshot.id,
                    characters = documentSnapshot.get("charactersIDs")
                )
                    .toModel()
            }
    }

    override suspend fun setCardByRoomAndUserID(
        roomId: String,
        userId: String,
        charactersIDs: List<String>
    ): Result<Unit> {
        try {
            rootCollection
                .document(roomId)
                .collection("cards")
                .document(userId)
                .update(data = hashMapOf("charactersIDs" to charactersIDs))

            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}