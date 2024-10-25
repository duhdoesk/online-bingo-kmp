package data.theme.repository

import data.character.model.CharacterDTO
import data.theme.model.BingoThemeDTO
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.character.model.Character
import domain.theme.model.BingoTheme
import domain.theme.repository.BingoThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class BingoThemeRepositoryImpl(
    firestore: FirebaseFirestore
) : BingoThemeRepository {

    private val collection = firestore
        .collection("themes")

    override suspend fun getThemeById(id: String): Result<BingoThemeDTO> {
        try {
            val themeDTO = collection
                .document(id)
                .get()
                .let { documentSnapshot ->
                    BingoThemeDTO(
                        id = documentSnapshot.id,
                        name = documentSnapshot.get("name"),
                        picture = documentSnapshot.get("picture"),
                        nameEnglish = documentSnapshot.get("name_en"),
                    )
                }
            return Result.success(themeDTO)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun flowThemeById(id: String): Flow<BingoTheme> {
        return collection
            .document(id)
            .snapshots
            .map { documentSnapshot ->
                BingoThemeDTO(
                    id = documentSnapshot.id,
                    name = documentSnapshot.get("name"),
                    picture = documentSnapshot.get("picture"),
                    nameEnglish = documentSnapshot.get("name_en"),
                ).toModel()
            }
    }

    override fun getAllThemes(): Flow<List<BingoTheme>> {
        return collection
            .snapshots
            .map { querySnapshot ->
                querySnapshot.documents.map { documentSnapshot ->
                    BingoThemeDTO(
                        id = documentSnapshot.id,
                        name = documentSnapshot.get("name"),
                        picture = documentSnapshot.get("picture"),
                        nameEnglish = documentSnapshot.get("name_en"),
                    ).toModel()
                }
            }
    }

    override fun flowThemeCharacters(themeId: String): Flow<List<Character>> {
        return flow {
            val characters = collection
                .document(themeId)
                .collection("characters")
                .get()
                .documents
                .map { document ->
                    Character(
                        id = document.id,
                        name = document.get("name"),
                        pictureUri = document.get("picture")
                    )
                }
            emit(characters)
        }
    }

    override suspend fun getThemeCharacters(themeId: String): Result<List<CharacterDTO>> {
        try {
            val characters = collection
                .document(themeId)
                .collection("characters")
                .get()
                .documents
                .map { document ->
                    CharacterDTO(
                        id = document.id,
                        name = document.get("name"),
                        pictureUri = document.get("picture"),
                    )
                }
            return Result.success(characters)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}