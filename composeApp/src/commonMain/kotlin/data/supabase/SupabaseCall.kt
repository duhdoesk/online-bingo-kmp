package data.supabase

import domain.util.resource.Cause
import domain.util.resource.Resource
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.runBlocking
import okio.IOException
import util.Log

internal inline fun <reified T> supabaseSuspendCall(crossinline apiCall: suspend () -> T): Flow<Resource<T>> =
    flow {
        try {
            val result = runBlocking { apiCall() }
            emit(Resource.Success(result))
        } catch (exception: Exception) {
            Log.e(
                message = "Supabase call failed",
                throwable = exception,
                tag = "Supabase Suspend Call"
            )

            val cause = when (exception) {
                is IOException -> Cause.NETWORK_ERROR
                is RestException -> Cause.API_ERROR
                else -> Cause.UNKNOWN
            }

            emit(Resource.Failure(cause))
        }
    }
        .retryWhen { cause, attempt ->
            if (cause is kotlinx.io.IOException && attempt <= 1) {
                Log.d(
                    message = "Tentativa ${attempt + 1} após falha de rede: ${cause.message}",
                    tag = "Supabase Suspend Call"
                )
                true
            } else {
                false
            }
        }

internal inline fun <reified T> supabaseCall(crossinline apiCall: () -> Flow<T>): Flow<Resource<T>> {
    return apiCall()
        .map { Resource.Success(it) }
        .catch { exception ->
            Log.e(
                message = "Supabase call failed",
                throwable = exception,
                tag = "Supabase Call"
            )

            val cause = when (exception) {
                is IOException -> Cause.NETWORK_ERROR
                is RestException -> Cause.API_ERROR
                else -> Cause.UNKNOWN
            }

            Resource.Failure(cause)
        }
        .retryWhen { cause, attempt ->
            if (cause is kotlinx.io.IOException && attempt <= 1) {
                Log.d(
                    message = "Tentativa ${attempt + 1} após falha de rede: ${cause.message}",
                    tag = "Supabase Call"
                )
                true
            } else {
                false
            }
        }
}
