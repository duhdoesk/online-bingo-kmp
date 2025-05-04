package domain.util.resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed interface Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>
    data class Failure(val cause: Cause) : Resource<Nothing>

    /**
     * Returns the data if the resource is a success, otherwise null.
     */
    fun getOrNull(): T? {
        return when (this) {
            is Success -> data
            else -> null
        }
    }

    /**
     * On success, transform function is used to map from one type of data to another.
     * On any error type, the same error type is returned.
     */
    fun <R> map(transformSuccess: (data: T) -> R): Resource<R> = when (this) {
        is Success -> Success(transformSuccess(this.data))
        is Failure -> Failure(this.cause)
    }
}

fun <I, O> Flow<Resource<I>>.map(block: (I) -> O): Flow<Resource<O>> {
    return map { it.map(block) }
}

fun <T> Flow<Resource<T>>.asUnit(): Flow<Resource<Unit>> =
    map { resource ->
        when (resource) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Failure -> resource
        }
    }
