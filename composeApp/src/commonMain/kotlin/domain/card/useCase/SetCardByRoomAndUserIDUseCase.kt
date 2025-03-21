package domain.card.useCase

import domain.card.repository.CardRepository

class SetCardByRoomAndUserIDUseCase(private val cardRepository: CardRepository) {
    suspend operator fun invoke(
        roomId: String,
        userId: String,
        charactersIDs: List<String>
    ): Result<Unit> {
        return cardRepository
            .getCardByRoomAndUserID(roomId, userId)
            .fold(
                onFailure = { exception ->
                    Result.failure(exception)
                },
                onSuccess = { card ->
                    if (card.characters == null) {
                        cardRepository.addCardByRoomAndUserID(
                            roomId,
                            userId,
                            charactersIDs
                        )
                    } else {
                        cardRepository.updateCardByRoomAndUserID(
                            roomId,
                            userId,
                            charactersIDs
                        )
                    }
                }
            )
    }
}
