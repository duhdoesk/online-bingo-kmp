package data.supabase

import data.feature.auth.AuthRepositoryImpl
import domain.util.resource.Cause
import domain.util.resource.Resource
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
                tag = AuthRepositoryImpl::class.simpleName.toString()
            )

            val cause = when (exception) {
                is IOException -> Cause.NETWORK_ERROR
                is RestException -> Cause.API_ERROR
                else -> Cause.UNKNOWN
            }

            emit(Resource.Failure(cause))
        }
    }
