package domain.card.use_case

import domain.card.model.Card
import domain.card.repository.CardRepository
import kotlinx.coroutines.flow.Flow

class FlowCardByRoomAndUserIDUseCase(private val cardRepository: CardRepository) {
    operator fun invoke(
        roomId: String,
        userId: String,
    ) : Flow<Card?> {
        return cardRepository.flowCardByRoomAndUserID(roomId, userId)
    }
}