package data.theme.repository

import data.theme.model.BingoThemeDTO
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.theme.model.BingoTheme
import domain.theme.repository.BingoThemeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BingoThemeRepositoryImpl : BingoThemeRepository, KoinComponent {

    private val firestore: FirebaseFirestore by inject()

    private val collection = firestore
        .collection("themes")

    override suspend fun getThemeById(id: String): BingoTheme {
        return collection
            .document(id)
            .get()
            .data<BingoThemeDTO>()
            .toModel()
    }

    override suspend fun getAllThemes(): List<BingoTheme> {
        return collection
            .get()
            .documents
            .map { it.data<BingoThemeDTO>() }
            .map { it.toModel() }
    }
}