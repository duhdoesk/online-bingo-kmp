package data.theme.repository

import data.theme.model.BingoThemeDTO
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import domain.theme.model.BingoTheme
import domain.theme.repository.BingoThemeRepository

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

    override suspend fun getAllThemes(): List<BingoTheme> {
        return collection
            .get()
            .documents
            .map { documentSnapshot ->
                BingoThemeDTO(
                    id = documentSnapshot.id,
                    name = documentSnapshot.get("name"),
                    picture = documentSnapshot.get("picture")
                )
            }
            .map { it.toModel() }
    }
}