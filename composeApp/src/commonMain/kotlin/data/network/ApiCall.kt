package data.network

import data.user.repository.UserRepositoryImpl
import dev.gitlive.firebase.firestore.FirebaseFirestoreException
import domain.util.resource.Cause
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.withTimeout
import kotlinx.io.IOException
import util.Log
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.seconds

internal inline fun <reified T> apiCall(crossinline apiCall: suspend () -> T): Flow<ApiResult<T>> =
    flow {
        try {
            val response = withTimeout(15.seconds) { apiCall() }
            emit(ApiResult.Success(response))
        } catch (e: FirebaseFirestoreException) {
            Log.e(
                message = e.toString(),
                tag = FirebaseFirestoreException::class.simpleName.toString(),
                throwable = e
            )
            emit(ApiResult.Failure(cause = Cause.API_ERROR, exception = e))
        } catch (e: IOException) {
            Log.e(
                message = e.toString(),
                tag = IOException::class.simpleName.toString(),
                throwable = e
            )
            emit(ApiResult.Failure(cause = Cause.NETWORK_ERROR, exception = e))
        } catch (e: CancellationException) {
            Log.e(
                message = e.toString(),
                tag = CancellationException::class.simpleName.toString(),
                throwable = e
            )
            throw e
        } catch (e: Exception) {
            Log.e(
                message = e.toString(),
                tag = Exception::class.simpleName.toString(),
                throwable = e
            )
            emit(ApiResult.Failure(cause = Cause.UNKNOWN, exception = e))
        }
    }
        .retryWhen { cause, attempt ->
            if (cause is IOException && attempt < 1) {
                Log.d(
                    message = "Tentativa ${attempt + 1} após falha de rede: ${cause.message}",
                    tag = UserRepositoryImpl::class.simpleName.toString()
                )
                true
            } else {
                false
            }
        }
        .flowOn(Dispatchers.IO)

internal inline fun <reified T> flowApiCall(crossinline apiCall: suspend () -> Flow<T>): Flow<ApiResult<T>> =
    flow {
        try {
            emitAll(
                apiCall()
                    .map<T, ApiResult<T>> { ApiResult.Success(it) }
                    .catch { exception ->
                        val apiResult: ApiResult<T> = when (exception) {
                            is FirebaseFirestoreException -> {
                                Log.e(
                                    message = exception.toString(),
                                    tag = FirebaseFirestoreException::class.simpleName.toString(),
                                    throwable = exception
                                )
                                ApiResult.Failure(cause = Cause.API_ERROR, exception = exception)
                            }

                            is IOException -> {
                                Log.e(
                                    message = exception.toString(),
                                    tag = IOException::class.simpleName.toString(),
                                    throwable = exception
                                )
                                ApiResult.Failure(
                                    cause = Cause.NETWORK_ERROR,
                                    exception = exception
                                )
                            }

                            is CancellationException -> {
                                Log.e(
                                    message = exception.toString(),
                                    tag = CancellationException::class.simpleName.toString(),
                                    throwable = exception
                                )
                                throw exception
                            }

                            else -> {
                                Log.e(
                                    message = exception.toString(),
                                    tag = Exception::class.simpleName.toString(),
                                    throwable = exception
                                )
                                ApiResult.Failure(cause = Cause.UNKNOWN, exception = exception)
                            }
                        }
                        emit(apiResult)
                    }
            )
        } catch (e: Exception) {
            Log.e("Wrapper", "Outer error: ${e.message}", e)
            emit(ApiResult.Failure(cause = Cause.UNKNOWN, exception = e))
        }
    }
        .retryWhen { cause, attempt ->
            if (cause is IOException && attempt < 1) {
                Log.d(
                    message = "Tentativa ${attempt + 1} após falha de rede: ${cause.message}",
                    tag = UserRepositoryImpl::class.simpleName.toString()
                )
                true
            } else {
                false
            }
        }
        .flowOn(Dispatchers.IO)
