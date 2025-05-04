package data.theme.repository

import data.firebase.firebaseSuspendCall
import data.theme.model.BingoThemeDTO
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.theme.repository.BingoThemeRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BingoThemeRepositoryImpl(
    private val firestore: FirebaseFirestore
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

    override fun uploadNewTheme(
        name: String,
        nameEnglish: String,
        nameSpanish: String,
        picture: String,
        available: Boolean
    ): Flow<Resource<String>> {
        return firebaseSuspendCall {
            collection.add(
                data = hashMapOf(
                    "name" to name,
                    "name_en" to nameEnglish,
                    "name_sp" to nameSpanish,
                    "picture" to picture,
                    "available" to available
                )
            ).id
        }.map { apiResult -> apiResult.toResource { it } }
    }

    private fun buildBingoThemeDTO(documentSnapshot: DocumentSnapshot): BingoThemeDTO {
        return BingoThemeDTO(
            id = documentSnapshot.id,
            name = documentSnapshot.get("name"),
            picture = documentSnapshot.get("picture"),
            nameEnglish = documentSnapshot.get("name_en"),
            nameSpanish = documentSnapshot.get("name_sp")
        )
    }
}
