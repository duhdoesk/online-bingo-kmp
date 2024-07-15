package domain.user.repository

import domain.user.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(
        id: String,
        email: String,
        name: String,
    )

    suspend fun getUserById(id: String): User
    fun getListOfUsers(ids: List<String>): Flow<List<User>>

    suspend fun setUser(id: String, user: User)
    suspend fun updateUserName(id: String, name: String)
    suspend fun updateUserEmail(id: String, email: String)
    suspend fun updateUserPictureUri(id: String, pictureUri: String)
    suspend fun updateVictoryMessage(id: String, victoryMessage: String)
}