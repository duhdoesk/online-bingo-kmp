package data.theme.repository

import data.theme.model.BingoThemeDTO
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.character.model.Character
import domain.theme.model.BingoTheme
import domain.theme.repository.BingoThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BingoThemeRepositoryImpl(
    private val firestore: FirebaseFirestore
) : BingoThemeRepository {

    private val collection = firestore
        .collection("themes")

    override suspend fun getThemeById(id: String): BingoTheme {
        return collection
            .document(id)
            .get()
            .let { documentSnapshot ->
                BingoThemeDTO(
                    id = documentSnapshot.id,
                    name = documentSnapshot.get("name"),
                    picture = documentSnapshot.get("picture")
                )
            }
            .toModel()
    }

    override fun getAllThemes(): Flow<List<BingoTheme>> {
        return flow {
            val themes = collection
                .get()
                .documents
                .map { document ->
                    BingoThemeDTO(
                        id = document.id,
                        name = document.get("name"),
                        picture = document.get("picture")
                    ).toModel()
                }

            emit(themes)
        }
    }

    override fun getCharacters(themeId: String): Flow<List<Character>> {
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
}