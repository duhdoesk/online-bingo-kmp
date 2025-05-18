@file:OptIn(ExperimentalCoroutinesApi::class)

package data.feature.user

import data.dispatcher.DispatcherProvider
import data.feature.user.mapper.toUserModel
import data.firebase.firebaseCall
import data.firebase.firebaseSuspendCall
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Timestamp
import domain.feature.auth.AuthRepository
import domain.feature.user.UserRepository
import domain.feature.user.model.User
import domain.util.resource.Cause
import domain.util.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.take

class UserRepositoryImplFirestore(
    firestore: FirebaseFirestore,
    private val authRepository: AuthRepository,
    private val dispatcherProvider: DispatcherProvider
) : UserRepository {

    private val collection = firestore.collection("users")

    override fun createUser(
        id: String,
        email: String,
        name: String,
        pictureUri: String,
        message: String
    ): Flow<Resource<Unit>> {
        return firebaseSuspendCall {
            collection
                .document(id)
                .set(
                    mapOf(
                        "email" to email,
                        "name" to name,
                        "pictureUri" to pictureUri,
                        "victoryMessage" to message,
                        "createdAt" to Timestamp.now()
                    )
                )
        }.flowOn(dispatcherProvider.io)
    }

    override fun getCurrentUser(): Flow<Resource<User>> {
        return authRepository.getSessionInfo()
            .take(1)
            .flatMapLatest { authResource ->
                when (authResource) {
                    is Resource.Success -> {
                        getUserById(authResource.data.id)
                    }

                    is Resource.Failure -> {
                        flowOf(Resource.Failure(Cause.USER_NOT_AUTHENTICATED))
                    }
                }
            }
            .flowOn(dispatcherProvider.io)
    }

    override fun getUserById(id: String): Flow<Resource<User>> {
        return firebaseCall {
            collection
                .document(id)
                .snapshots
                .map { it.toUserModel() }
        }.flowOn(dispatcherProvider.io)
    }

    override fun getListOfUsers(ids: List<String>): Flow<Resource<List<User>>> {
        return firebaseCall {
            val flows = ids.map { id ->
                collection
                    .document(id)
                    .snapshots
                    .mapNotNull { if (it.exists) it.toUserModel() else null }
            }

            combine(flows) { it.toList() }
        }.flowOn(dispatcherProvider.io)
    }

    override fun updateUserName(name: String): Flow<Resource<Unit>> {
        return getCurrentUser()
            .take(1)
            .flatMapLatest { userResource ->
                when (userResource) {
                    is Resource.Success -> {
                        firebaseSuspendCall {
                            collection
                                .document(userResource.data.id)
                                .set(
                                    mapOf(
                                        "name" to name,
                                        "updatedAt" to Timestamp.now()
                                    )
                                )
                        }
                    }

                    is Resource.Failure -> {
                        flowOf(userResource)
                    }
                }
            }.flowOn(dispatcherProvider.io)
    }

    override fun updateUserPictureUri(pictureUri: String): Flow<Resource<Unit>> {
        return getCurrentUser()
            .take(1)
            .flatMapLatest { userResource ->
                when (userResource) {
                    is Resource.Success -> {
                        firebaseSuspendCall {
                            collection
                                .document(userResource.data.id)
                                .set(
                                    mapOf(
                                        "pictureUri" to pictureUri,
                                        "updatedAt" to Timestamp.now()
                                    )
                                )
                        }
                    }

                    is Resource.Failure -> {
                        flowOf(userResource)
                    }
                }
            }.flowOn(dispatcherProvider.io)
    }

    override fun updateVictoryMessage(victoryMessage: String): Flow<Resource<Unit>> {
        return getCurrentUser()
            .take(1)
            .flatMapLatest { userResource ->
                when (userResource) {
                    is Resource.Success -> {
                        firebaseSuspendCall {
                            collection
                                .document(userResource.data.id)
                                .set(
                                    mapOf(
                                        "victoryMessage" to victoryMessage,
                                        "updatedAt" to Timestamp.now()
                                    )
                                )
                        }
                    }

                    is Resource.Failure -> {
                        flowOf(userResource)
                    }
                }.flowOn(dispatcherProvider.io)
            }
    }

    override fun deleteUser(id: String): Flow<Resource<Unit>> {
        return authRepository.deleteAccount(id)
            .take(1)
            .flatMapLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        firebaseSuspendCall {
                            collection
                                .document(id)
                                .delete()
                        }
                    }

                    is Resource.Failure -> {
                        flowOf(resource)
                    }
                }
            }
            .flowOn(dispatcherProvider.io)
    }
}
