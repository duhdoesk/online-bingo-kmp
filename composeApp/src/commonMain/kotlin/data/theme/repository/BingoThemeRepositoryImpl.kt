package data.theme.repository

import data.theme.model.BingoThemeDTO
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.theme.repository.BingoThemeRepository
import kotlinx.coroutines.flow.Flow
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
                    buildBingoThemeDTO(documentSnapshot)
                }
            return Result.success(themeDTO)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun flowThemeById(id: String): Flow<BingoThemeDTO> {
        return collection
            .document(id)
            .snapshots
            .map { documentSnapshot ->
                buildBingoThemeDTO(documentSnapshot)
            }
    }

    override fun getAllThemes(): Flow<List<BingoThemeDTO>> {
        return collection
            .snapshots
            .map { querySnapshot ->
                querySnapshot.documents.map { documentSnapshot ->
                    buildBingoThemeDTO(documentSnapshot)
                }
            }
    }

    override fun observeAvailableThemes(): Flow<List<BingoThemeDTO>> {
        return collection
            .where { "available" equalTo true }
            .snapshots
            .map { querySnapshot ->
                querySnapshot.documents.map { documentSnapshot ->
                    buildBingoThemeDTO(documentSnapshot)
                }
            }
    }

    private fun buildBingoThemeDTO(documentSnapshot: DocumentSnapshot): BingoThemeDTO {
        return BingoThemeDTO(
            id = documentSnapshot.id,
            name = documentSnapshot.get("name"),
            picture = documentSnapshot.get("picture"),
            nameEnglish = documentSnapshot.get("name_en")
        )
    }
}
