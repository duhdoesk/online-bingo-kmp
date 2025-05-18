@file:OptIn(FlowPreview::class)

package data.firebase

import domain.util.resource.Cause
import domain.util.resource.Resource
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.timeout
import kotlinx.io.IOException
import util.Log
import kotlin.time.Duration.Companion.seconds

internal inline fun <reified T> firebaseSuspendCall(crossinline apiCall: suspend () -> T): Flow<Resource<T>> =
    flow<Resource<T>> {
        val response = apiCall()
        emit(Resource.Success(response))
    }
        .timeout(15.seconds)
        .retryWhen { cause, attempt ->
            if (cause is IOException && attempt <= 1) {
                Log.d(
                    message = "Tentativa ${attempt + 1} após falha de rede: ${cause.message}",
                    tag = "Firebase Suspend Call"
                )
                true
            } else {
                false
            }
        }
        .catch { exception ->
            Log.e(
                message = "Firebase call failed",
                throwable = exception,
                tag = "Firebase Suspend Call"
            )

            val cause = when (exception) {
                is HttpRequestException -> Cause.NETWORK_ERROR
                is RestException -> Cause.API_ERROR
                else -> Cause.UNKNOWN
            }

            emit(Resource.Failure(cause))
        }

internal inline fun <reified T> firebaseCall(crossinline apiCall: () -> Flow<T>): Flow<Resource<T>> {
    return apiCall()
        .retryWhen { cause, attempt ->
            if (cause is IOException && attempt <= 1) {
                Log.d(
                    message = "Tentativa ${attempt + 1} após falha de rede: ${cause.message}",
                    tag = "Firebase Suspend Call"
                )
                true
            } else {
                false
            }
        }
        .map<T, Resource<T>> { Resource.Success(it) }
        .catch { exception ->
            Log.e(
                message = "Firebase call failed",
                throwable = exception,
                tag = "Firebase Suspend Call"
            )

            val cause = when (exception) {
                is HttpRequestException -> Cause.NETWORK_ERROR
                is RestException -> Cause.API_ERROR
                else -> Cause.UNKNOWN
            }

            emit(Resource.Failure(cause))
        }
}
