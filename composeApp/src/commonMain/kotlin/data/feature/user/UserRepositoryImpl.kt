@file:OptIn(ExperimentalCoroutinesApi::class)

package data.feature.user

import data.feature.user.mapper.toUserModel
import data.firebase.firebaseCall
import data.firebase.firebaseSuspendCall
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.feature.auth.AuthRepository
import domain.feature.user.UserRepository
import domain.feature.user.model.User
import domain.util.resource.Cause
import domain.util.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import util.getLocalDateTimeNow

class UserRepositoryImpl(
    firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) : UserRepository {

    private val collection = firestore.collection("users")

    override fun createUser(
        id: String,
        email: String,
        name: String,
        pictureUri: String,
        message: String
    ): Flow<Resource<Unit>> {
        val now = getLocalDateTimeNow().toString()

        return firebaseSuspendCall {
            collection
                .document(id)
                .set(
                    data = hashMapOf(
                        "createdAt" to now,
                        "name" to name,
                        "nameUpdatedAt" to now,
                        "email" to email,
                        "pictureUri" to pictureUri,
                        "pictureUriUpdatedAt" to now,
                        "victoryMessage" to message,
                        "victoryMessageUpdatedAt" to now,
                        "lastWinAt" to null
                    )
                )
        }.map { it.toResource { it } }
    }

    override fun getCurrentUser(): Flow<Resource<User>> =
        authRepository
            .getSessionInfo()
            .flatMapLatest { authResource ->
                when (authResource) {
                    is Resource.Success -> {
                        firebaseCall {
                            collection
                                .document(authResource.data.id)
                                .snapshots
                                .map { it.toUserModel() }
                        }.map { it.toResource { it } }
                    }

                    is Resource.Failure -> {
                        flowOf(Resource.Failure(Cause.USER_NOT_AUTHENTICATED))
                    }
                }
            }

    override fun getUserById(id: String): Flow<Resource<User>> =
        firebaseCall {
            collection
                .document(id)
                .snapshots
                .map { it.toUserModel() }
        }.map { it.toResource { it } }

    override fun getListOfUsers(ids: List<String>): Flow<Resource<List<User>>> =
        firebaseCall {
            collection
                .snapshots
                .map { querySnapshot ->
                    querySnapshot
                        .documents
                        .filter { documentSnapshot -> ids.contains(documentSnapshot.id) }
                        .map { it.toUserModel() }
                }
        }.map { it.toResource { it } }

    override fun updateUserName(id: String, name: String): Flow<Resource<Unit>> =
        firebaseSuspendCall {
            collection
                .document(id)
                .update(
                    data = hashMapOf(
                        "name" to name,
                        "nameUpdatedAt" to getLocalDateTimeNow().toString()
                    )
                )
        }.map { it.toResource { it } }

    override fun updateUserPictureUri(id: String, pictureUri: String): Flow<Resource<Unit>> =
        firebaseSuspendCall {
            collection
                .document(id)
                .update(
                    data = hashMapOf(
                        "pictureUri" to pictureUri,
                        "pictureUriUpdatedAt" to getLocalDateTimeNow().toString()
                    )
                )
        }.map { it.toResource { it } }

    override fun updateVictoryMessage(id: String, victoryMessage: String): Flow<Resource<Unit>> =
        firebaseSuspendCall {
            collection
                .document(id)
                .update(
                    data = hashMapOf(
                        "victoryMessage" to victoryMessage,
                        "victoryMessageUpdatedAt" to getLocalDateTimeNow().toString()
                    )
                )
        }.map { it.toResource { it } }

    override fun deleteUser(id: String): Flow<Resource<Unit>> =
        authRepository
            .deleteAccount(id)
            .flatMapLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        firebaseSuspendCall {
                            collection
                                .document(id)
                                .delete()
                        }.map { it.toResource { it } }
                    }

                    is Resource.Failure -> {
                        flowOf(resource)
                    }
                }
            }
}
