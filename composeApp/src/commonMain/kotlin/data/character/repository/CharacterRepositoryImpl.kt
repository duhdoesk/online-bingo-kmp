package data.character.repository

import data.character.model.CharacterDTO
import data.firebase.firebaseCall
import data.firebase.firebaseSuspendCall
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.QuerySnapshot
import domain.character.model.Character
import domain.character.repository.CharacterRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(firestore: FirebaseFirestore) : CharacterRepository {

    private val collection = firestore.collection("themes")

    override fun getThemeCharacters(themeId: String): Flow<Resource<List<Character>>> {
        return firebaseCall {
            collection
                .document(themeId)
                .collection("characters")
                .snapshots
                .map { querySnapshot: QuerySnapshot ->
                    querySnapshot.documents.map { documentSnapshot: DocumentSnapshot ->
                        buildCharacterDTO(documentSnapshot).toModel()
                    }
                }
        }
    }

    override fun uploadNewCharacter(
        themeId: String,
        name: String,
        nameEnglish: String,
        nameSpanish: String,
        picture: String
    ): Flow<Resource<Unit>> {
        return firebaseSuspendCall {
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
        }
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
