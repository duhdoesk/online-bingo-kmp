package data.auth.supabase

import domain.auth.supabase.SupabaseAuthService
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.user.UserInfo

class SupabaseAuthServiceImpl(
    override val supabaseClient: SupabaseClient
) : SupabaseAuthService {

    override val sessionStatus = supabaseClient.auth.sessionStatus

    override val currentUser: UserInfo?
        get() = supabaseClient.auth.currentUserOrNull()

    override suspend fun signInWithGoogle() {
        supabaseClient.auth.signInWith(Google)
    }

    override suspend fun signOut(): Result<Unit> {
        try {
            supabaseClient.auth.signOut()
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun deleteAccount(uid: String): Result<Unit> {
        try {
            supabaseClient.auth.signOut()
            supabaseClient.auth.importAuthToken(SERVICE_ROLE_KEY)
            supabaseClient.auth.admin.deleteUser(uid = uid)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
