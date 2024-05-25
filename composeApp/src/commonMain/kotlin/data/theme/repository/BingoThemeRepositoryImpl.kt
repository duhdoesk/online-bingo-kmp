package data.theme.repository

import data.theme.model.BingoThemeDTO
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.theme.model.BingoTheme
import domain.theme.model.mockBingoThemeList
import domain.theme.repository.BingoThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BingoThemeRepositoryImpl() : BingoThemeRepository {

    val collection = Firebase
        .firestore
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