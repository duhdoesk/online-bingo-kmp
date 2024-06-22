package data.card.repository

import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.card.repository.CardRepository

class CardRepositoryImpl(
    private val firestore: FirebaseFirestore
): CardRepository {
}