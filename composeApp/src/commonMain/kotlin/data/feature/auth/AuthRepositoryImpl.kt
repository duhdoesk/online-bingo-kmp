package data.feature.auth

import data.dispatcher.DispatcherProvider
import data.supabase.SERVICE_ROLE_KEY
import data.supabase.supabaseSuspendCall
import domain.feature.auth.AuthRepository
import domain.util.resource.Cause
import domain.util.resource.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import util.Log

class AuthRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val supabaseClient: SupabaseClient
) : AuthRepository {

    override fun deleteAccount(uid: String): Flow<Resource<Unit>> =
        supabaseSuspendCall {
            supabaseClient.auth.importAuthToken(SERVICE_ROLE_KEY)
            supabaseClient.auth.admin.deleteUser(uid)
        }.flowOn(dispatcherProvider.io)

    override fun getSessionInfo(): Flow<Resource<UserInfo>> =
        supabaseClient.auth.sessionStatus
            .map { status ->
                if (status is SessionStatus.Authenticated) {
                    status.session.user?.let { user -> Resource.Success(user) }
                        ?: Resource.Failure(Cause.USER_NOT_FOUND)
                } else {
                    Resource.Failure(Cause.USER_NOT_AUTHENTICATED)
                }
            }
            .catch { exception ->
                Log.e(
                    message = "Error getting user info",
                    throwable = exception,
                    tag = AuthRepositoryImpl::class.simpleName.toString()
                )
                Resource.Failure(Cause.UNKNOWN)
            }
            .flowOn(dispatcherProvider.io)

    override fun getSessionStatus(): Flow<Resource<SessionStatus>> =
        supabaseClient.auth.sessionStatus
            .map { status ->
                Resource.Success(status)
            }
            .catch { exception ->
                Log.e(
                    message = "Error getting session status",
                    throwable = exception,
                    tag = AuthRepositoryImpl::class.simpleName.toString()
                )
                Resource.Failure(Cause.UNKNOWN)
            }
            .flowOn(dispatcherProvider.io)

    override fun signOut(): Flow<Resource<Unit>> =
        supabaseSuspendCall {
            supabaseClient.auth.signOut()
        }.flowOn(dispatcherProvider.io)
}
