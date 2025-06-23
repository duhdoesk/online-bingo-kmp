@file:OptIn(ExperimentalCoroutinesApi::class)

package data.theme.repository

import data.firebase.firebaseCall
import data.firebase.firebaseSuspendCall
import data.theme.model.BingoThemeDTO
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.theme.model.Theme
import domain.theme.repository.ThemeRepository
import domain.util.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeRepositoryImpl(firestore: FirebaseFirestore) : ThemeRepository {

    private val collection = firestore.collection("themes")

    override fun getThemeById(id: String): Flow<Resource<Theme>> {
        return firebaseCall {
            collection.document(id)
                .snapshots
                .map { documentSnapshot ->
                    buildBingoThemeDTO(documentSnapshot).toModel()
                }
        }
    }

    override fun getAllThemes(): Flow<Resource<List<Theme>>> {
        return firebaseCall {
            collection
                .snapshots
                .map { querySnapshot ->
                    querySnapshot.documents.map { documentSnapshot ->
                        buildBingoThemeDTO(documentSnapshot).toModel()
                    }
                }
        }
    }

    override fun getAvailableThemes(): Flow<Resource<List<Theme>>> {
        return firebaseCall {
            collection
                .where { "available" equalTo true }
                .snapshots
                .map { querySnapshot ->
                    querySnapshot.documents.map { documentSnapshot ->
                        buildBingoThemeDTO(documentSnapshot).toModel()
                    }
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
        }
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
