package data.firebase

import domain.util.resource.Cause
import domain.util.resource.Resource

sealed class FirebaseResult<T> {
    data class Success<T>(val data: T) : FirebaseResult<T>()

    data class Failure<T>(val cause: Cause, val exception: Throwable?) : FirebaseResult<T>()

    fun <R> toResource(transformSuccess: (data: T) -> R): Resource<R> = when (this) {
        is Success -> Resource.Success(transformSuccess(data))
        is Failure -> Resource.Failure(cause)
    }
}
