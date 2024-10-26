package data.character.repository

import data.character.model.CharacterDTO
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.QuerySnapshot
import domain.character.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(
    firestore: FirebaseFirestore
) : CharacterRepository {

    private val collection = firestore
        .collection("themes")

    override fun observeThemeCharacters(themeId: String): Flow<List<CharacterDTO>> {
        return collection
            .document(themeId)
            .collection("characters")
            .snapshots
            .map { querySnapshot: QuerySnapshot ->
                querySnapshot.documents.map { documentSnapshot: DocumentSnapshot ->
                    buildCharacterDTO(documentSnapshot)
                }
            }
    }

    override suspend fun getThemeCharacters(themeId: String): Result<List<CharacterDTO>> {
        try {
            val characters = collection
                .document(themeId)
                .collection("characters")
                .get()
                .documents
                .map { documentSnapshot ->
                    buildCharacterDTO(documentSnapshot)
                }
            return Result.success(characters)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private fun buildCharacterDTO(documentSnapshot: DocumentSnapshot): CharacterDTO {
        return CharacterDTO(
            id = documentSnapshot.id,
            name = documentSnapshot.get("name"),
            nameInEnglish = documentSnapshot.get("name_en"),
            pictureUri = documentSnapshot.get("picture"),
        )
    }
}