@file:OptIn(ExperimentalCoroutinesApi::class)

package data.user.repository

import data.network.ApiResult
import data.network.apiCall
import data.network.flowApiCall
import data.user.model.UserDTO
import data.user.model.toUserDTO
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Timestamp
import domain.feature.auth.useCase.GetSessionInfoUseCase
import domain.user.model.User
import domain.user.repository.UserRepository
import domain.util.resource.Cause
import domain.util.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    firestore: FirebaseFirestore,
    private val getSessionInfoUseCase: GetSessionInfoUseCase
) : UserRepository {

    private val collection = firestore.collection("users")

    override fun getSignedInUser(): Flow<Resource<User>> {
        return getSessionInfoUseCase()
            .flatMapLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        flowApiCall {
                            collection
                                .document(resource.data.id)
                                .snapshots
                                .map { it.toUserDTO().toModel() }
                        }
                            .map { it.toResource { it } }
                    }

                    is Resource.Failure -> {
                        flowOf<ApiResult<User>>(
                            ApiResult.Failure(Cause.USER_NOT_AUTHENTICATED, null)
                        ).map { it.toResource { it } }
                    }
                }
            }
    }

    override fun getUserById(id: String): Flow<Resource<User>> = flowApiCall {
        collection
            .document(id)
            .snapshots
            .map { it.toUserDTO().toModel() }
    }.map { it.toResource { it } }

    override fun createUser(
        id: String,
        email: String,
        name: String,
        pictureUri: String,
        message: String
    ): Flow<Resource<Unit>> {
        return apiCall {
            collection
                .document(id)
                .set(
                    data = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "pictureUri" to pictureUri,
                        "victoryMessage" to message
                    )
                )
        }.map { it.toResource { it } }
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
                            victoryMessageLastUpdated = documentSnapshot.get("victoryMessageLastUpdated")
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
                    "pictureUri" to user.pictureUri
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

    override suspend fun checkIfUserExists(id: String): Result<Boolean> {
        return try {
            Result.success(collection.document(id).get().exists)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
