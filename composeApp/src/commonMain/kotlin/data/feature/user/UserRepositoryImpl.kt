@file:OptIn(ExperimentalCoroutinesApi::class, SupabaseExperimental::class)

package data.feature.user

import data.dispatcher.DispatcherProvider
import data.feature.user.model.UserDto
import data.supabase.supabaseCall
import data.supabase.supabaseSuspendCall
import domain.feature.auth.AuthRepository
import domain.feature.user.UserRepository
import domain.feature.user.model.Tier
import domain.feature.user.model.User
import domain.util.resource.Cause
import domain.util.resource.Resource
import domain.util.resource.asUnit
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import io.github.jan.supabase.realtime.selectSingleValueAsFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import util.getLocalDateTimeNow

class UserRepositoryImpl(
    private val authRepository: AuthRepository,
    private val dispatcherProvider: DispatcherProvider,
    supabase: SupabaseClient
) : UserRepository {

    private val usersTable = supabase.from("users")

    override fun createUser(
        id: String,
        email: String,
        name: String,
        pictureUri: String,
        message: String
    ): Flow<Resource<Unit>> {
        val user = UserDto(
            id = id,
            email = email,
            createdAt = getLocalDateTimeNow().toString(),
            updatedAt = null,
            name = name,
            message = message,
            pictureUrl = pictureUri,
            tier = Tier.FREE.name
        )

        return supabaseSuspendCall { usersTable.insert(user) }
            .asUnit()
            .flowOn(dispatcherProvider.io)
    }

    override fun getCurrentUser(): Flow<Resource<User>> =
        authRepository.getSessionInfo()
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

    override fun getUserById(id: String): Flow<Resource<User>> =
        supabaseCall {
            usersTable.selectSingleValueAsFlow(UserDto::id) { eq("id", id) }.map { it.toModel() }
        }.flowOn(dispatcherProvider.io)

    override fun getListOfUsers(ids: List<String>): Flow<Resource<List<User>>> =
        supabaseCall {
            usersTable.selectAsFlow(
                UserDto::id,
                filter = FilterOperation("id", FilterOperator.IN, ids)
            ).map { it.map { it.toModel() } }
        }

    override fun updateUserName(id: String, name: String): Flow<Resource<Unit>> =
        supabaseSuspendCall {
            usersTable.update(
                {
                    UserDto::name setTo name
                    UserDto::updatedAt setTo getLocalDateTimeNow().toString()
                }
            ) { filter { UserDto::id eq id } }
        }

    override fun updateUserPictureUri(id: String, pictureUri: String): Flow<Resource<Unit>> =
        supabaseSuspendCall {
            usersTable.update(
                {
                    UserDto::pictureUrl setTo pictureUri
                    UserDto::updatedAt setTo getLocalDateTimeNow().toString()
                }
            ) { filter { UserDto::id eq id } }
        }

    override fun updateVictoryMessage(id: String, victoryMessage: String): Flow<Resource<Unit>> =
        supabaseSuspendCall {
            usersTable.update(
                {
                    UserDto::message setTo victoryMessage
                    UserDto::updatedAt setTo getLocalDateTimeNow().toString()
                }
            ) { filter { UserDto::id eq id } }
        }

    override fun deleteUser(id: String): Flow<Resource<Unit>> =
        authRepository.deleteAccount(id)
            .flatMapLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        supabaseSuspendCall { usersTable.delete { filter { UserDto::id eq id } } }
                            .asUnit()
                    }

                    is Resource.Failure -> {
                        flowOf(resource)
                    }
                }
            }
}
