package domain.user.repository

import domain.user.model.User
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun createUser(
        id: String,
        email: String,
        name: String,
        pictureUri: String,
        message: String
    ): Flow<Resource<Unit>>

    fun getSignedInUser(): Flow<Resource<User>>
    fun getUserById(id: String): Flow<Resource<User>>
    fun getListOfUsers(ids: List<String>): Flow<List<User>>

    suspend fun setUser(id: String, user: User)
    suspend fun updateUserName(id: String, name: String): Result<Unit>
    suspend fun updateUserEmail(id: String, email: String): Result<Unit>
    suspend fun updateUserPictureUri(id: String, pictureUri: String): Result<Unit>
    suspend fun updateVictoryMessage(id: String, victoryMessage: String): Result<Unit>
    suspend fun deleteUser(id: String): Result<Unit>
    suspend fun checkIfUserExists(id: String): Result<Boolean>
}
