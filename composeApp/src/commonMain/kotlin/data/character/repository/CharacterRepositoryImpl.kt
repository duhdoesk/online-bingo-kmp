package data.character.repository

import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.character.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val firestore: FirebaseFirestore
): CharacterRepository {
}