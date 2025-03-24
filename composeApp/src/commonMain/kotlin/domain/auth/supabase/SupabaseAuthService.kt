package domain.auth.supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface SupabaseAuthService {
    val supabaseClient: SupabaseClient
    val currentUser: UserInfo?
    val sessionStatus: StateFlow<SessionStatus>
    suspend fun signInWithGoogle()
    suspend fun signOut(): Result<Unit>
    suspend fun deleteAccount(uid: String): Result<Unit>
}
