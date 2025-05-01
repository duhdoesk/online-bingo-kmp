package data.character.repository

import data.character.model.CharacterDTO
import data.network.apiCall
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.QuerySnapshot
import domain.character.repository.CharacterRepository
import domain.util.resource.Resource
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

    override fun uploadNewCharacter(
        themeId: String,
        name: String,
        nameEnglish: String,
        nameSpanish: String,
        picture: String
    ): Flow<Resource<Unit>> {
        return apiCall {
            collection
                .document(themeId)
                .collection("characters")
                .add(
                    data = hashMapOf(
                        "name" to name,
                        "name_en" to nameEnglish,
                        "name_sp" to nameSpanish,
                        "picture" to picture
                    )
                )
        }.map { apiResult -> apiResult.toResource { } }
    }

    private fun buildCharacterDTO(documentSnapshot: DocumentSnapshot): CharacterDTO {
        return CharacterDTO(
            id = documentSnapshot.id,
            name = documentSnapshot.get("name"),
            nameInEnglish = documentSnapshot.get("name_en"),
            nameInSpanish = documentSnapshot.get("name_sp"),
            pictureUri = documentSnapshot.get("picture")
        )
    }
}
