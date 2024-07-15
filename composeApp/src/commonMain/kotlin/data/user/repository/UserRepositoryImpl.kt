package data.user.repository

import data.user.model.UserDTO
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.user.model.User
import domain.user.repository.UserRepository
import domain.util.datetime.getCurrentDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    firestore: FirebaseFirestore
) : UserRepository {

    private val collection = firestore.collection("users")

    override suspend fun getUserById(id: String): User {
        return collection
            .document(id)
            .get()
            .let { documentSnapshot ->
                UserDTO(
                    id = documentSnapshot.id,
                    name = documentSnapshot.get("name"),
                    pictureUri = documentSnapshot.get("pictureUri"),
                    email = documentSnapshot.get("email"),
                    nameLastUpdated = documentSnapshot.get("nameLastUpdated"),
                    pictureUriLastUpdated = documentSnapshot.get("pictureUriLastUpdated"),
                    lastWinTimestamp = documentSnapshot.get("lastWinTimestamp"),
                    victoryMessage = documentSnapshot.get("victoryMessage"),
                    victoryMessageLastUpdated = documentSnapshot.get("victoryMessageLastUpdated"),
                )
            }
            .toModel()
    }

    override suspend fun createUser(
        id: String,
        email: String,
        name: String,
    ) {
        collection
            .document(id)
            .set(
                data = hashMapOf(
                    "name" to name,
                    "email" to email,
                )
            )
    }

    override fun getListOfUsers(ids: List<String>): Flow<List<User>> {
        return collection
            .snapshots
            .map { querySnapshot ->
                querySnapshot
                    .documents
                    .filter { documentSnapshot ->
                        ids.contains(documentSnapshot.id)
                    }
                    .map { documentSnapshot ->
                        UserDTO(
                            id = documentSnapshot.id,
                            name = documentSnapshot.get("name"),
                            pictureUri = documentSnapshot.get("pictureUrl"),
                            email = documentSnapshot.get("email"),
                            nameLastUpdated = documentSnapshot.get("nameLastUpdated"),
                            pictureUriLastUpdated = documentSnapshot.get("pictureUriLastUpdated"),
                            lastWinTimestamp = documentSnapshot.get("lastWinTimestamp"),
                            victoryMessage = documentSnapshot.get("victoryMessage"),
                            victoryMessageLastUpdated = documentSnapshot.get("victoryMessageLastUpdated"),
                        )
                            .toModel()
                    }
            }
    }

    override suspend fun setUser(id: String, user: User) {
        collection
            .document(id)
            .set(
                data = hashMapOf(
                    "name" to user.name,
                    "email" to user.email,
                    "pictureUri" to user.pictureUri,
                ),
                merge = true
            )
    }

    override suspend fun updateUserName(id: String, name: String) {
        collection
            .document(id)
            .update(
                data = hashMapOf(
                    "name" to name,
                    "nameLastUpdated" to getCurrentDateTime()
                )
            )
    }

    override suspend fun updateUserEmail(id: String, email: String) {
        collection
            .document(id)
            .update(data = hashMapOf("email" to email))
    }

    override suspend fun updateUserPictureUri(id: String, pictureUri: String) {
        collection
            .document(id)
            .update(
                data = hashMapOf(
                    "pictureUri" to pictureUri,
                    "pictureUriLastUpdated" to getCurrentDateTime()
                )
            )
    }

    override suspend fun updateVictoryMessage(id: String, victoryMessage: String) {
        collection
            .document(id)
            .update(
                data = hashMapOf(
                    "victoryMessage" to victoryMessage,
                    "victoryMessageLastUpdated" to getCurrentDateTime()
                )
            )
    }
}