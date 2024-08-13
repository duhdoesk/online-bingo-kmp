package domain.card.use_case

import domain.card.repository.CardRepository

class SetCardByRoomAndUserIDUseCase(private val cardRepository: CardRepository) {
    suspend operator fun invoke(
        roomId: String,
        userId: String,
        charactersIDs: List<String>,
    ) : Result<Unit> {
        return cardRepository.setCardByRoomAndUserID(roomId, userId, charactersIDs)
    }
}