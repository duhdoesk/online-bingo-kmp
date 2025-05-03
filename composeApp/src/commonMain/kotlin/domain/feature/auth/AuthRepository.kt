package domain.feature.auth

import domain.util.resource.Resource
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    /** Deletes user account from Supabase */
    fun deleteAccount(uid: String): Flow<Resource<Unit>>

    /** Returns User Authentication-only Info */
    fun getSessionInfo(): Flow<Resource<UserInfo>>

    /** Returns the current session status */
    fun getSessionStatus(): Flow<Resource<SessionStatus>>

    /** Signs user out from Supabase */
    fun signOut(): Flow<Resource<Unit>>
}
