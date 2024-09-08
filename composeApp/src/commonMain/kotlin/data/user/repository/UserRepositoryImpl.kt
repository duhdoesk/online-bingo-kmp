package data.user.repository

import data.user.model.UserDTO
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Timestamp
import domain.user.model.User
import domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    firestore: FirebaseFirestore
) : UserRepository {

    private val collection = firestore.collection("users")

    override fun flowUser(id: String): Flow<User> {
        return collection
            .document(id)
            .snapshots
            .map { documentSnapshot ->
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
                ).toModel()
            }
    }

    override suspend fun getUserById(id: String): Result<User> {
        try {
            collection
                .document(id)
                .get()
                .let { documentSnapshot ->
                    val user = UserDTO(
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
                        .toModel()

                    return Result.success(user)
                }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun createUser(
        id: String,
        email: String,
        name: String,
    ): Result<Unit> {
        try {
            collection
                .document(id)
                .set(
                    data = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "pictureUri" to "https://i.imgur.com/DujcYDE.jpg",
                        "victoryMessage" to "O Bingo Temático é demais!"
                    )
                )
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
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
                            pictureUri = documentSnapshot.get("pictureUri"),
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

    override suspend fun updateUserName(id: String, name: String): Result<Unit> {
        try {
            collection
                .document(id)
                .update(
                    data = hashMapOf(
                        "name" to name,
                        "nameLastUpdated" to Timestamp.now()
                    )
                )
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun updateUserEmail(id: String, email: String): Result<Unit> {
        try {
            collection
                .document(id)
                .update(data = hashMapOf("email" to email))
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun updateUserPictureUri(id: String, pictureUri: String): Result<Unit> {
        try {
            collection
                .document(id)
                .update(
                    data = hashMapOf(
                        "pictureUri" to pictureUri,
                        "pictureUriLastUpdated" to Timestamp.now()
                    )
                )
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun updateVictoryMessage(id: String, victoryMessage: String): Result<Unit> {
        try {
            collection
                .document(id)
                .update(
                    data = hashMapOf(
                        "victoryMessage" to victoryMessage,
                        "victoryMessageLastUpdated" to Timestamp.now()
                    )
                )
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun deleteUser(id: String): Result<Unit> {
        try {
            collection
                .document(id)
                .delete()
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}