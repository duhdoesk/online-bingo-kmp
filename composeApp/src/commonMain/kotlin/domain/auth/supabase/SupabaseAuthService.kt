package domain.auth.supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface SupabaseAuthService {
    val supabaseClient: SupabaseClient
    val currentUser: UserInfo?
    val sessionStatus: StateFlow<SessionStatus>
    suspend fun signInWithGoogle()
    suspend fun signOut()
    suspend fun deleteAccount()
}