package domain.feature.user

import domain.feature.user.model.User
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    /** Creates a new user */
    fun createUser(
        id: String,
        email: String,
        name: String,
        pictureUri: String,
        message: String
    ): Flow<Resource<Unit>>

    /** Returns user info */
    fun getCurrentUser(): Flow<Resource<User>>

    /** Returns user info given it's ID */
    fun getUserById(id: String): Flow<Resource<User>>

    /** Returns a list of users given a list of IDs */
    fun getListOfUsers(ids: List<String>): Flow<Resource<List<User>>>

    /** Updates user name */
    fun updateUserName(id: String, name: String): Flow<Resource<Unit>>

    /** Updates user picture */
    fun updateUserPictureUri(id: String, pictureUri: String): Flow<Resource<Unit>>

    /** Updates user victory message */
    fun updateVictoryMessage(id: String, victoryMessage: String): Flow<Resource<Unit>>

    /** Deletes user */
    fun deleteUser(id: String): Flow<Resource<Unit>>
}
