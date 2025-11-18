package com.example.dialektapp.data.remote.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import com.example.dialektapp.domain.util.Result
import com.example.dialektapp.domain.util.NetworkError
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

inline fun <reified T> responseToResult(response: Response<T>): Result<T, NetworkError> {
    return if (response.isSuccessful) {
        val body = response.body()

        // Для 204 No Content body завжди null, але це успіх
        // Також для Unit типу body може бути null
        if (body != null || response.code() == 204 || T::class == Unit::class) {
            @Suppress("UNCHECKED_CAST")
            Result.Success(body ?: Unit as T)
        } else {
            Result.Error(NetworkError.SERIALIZATION_ERROR)
        }
    } else {
        when (response.code()) {
            401, 403 -> Result.Error(NetworkError.UNAUTHORIZED)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}

suspend inline fun <reified T> safeCall(crossinline apiCall: suspend () -> Response<T>): Result<T, NetworkError> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            responseToResult(response)
        } catch (e: UnknownHostException) {
            Result.Error(NetworkError.NO_INTERNET)
        } catch (e: ConnectException) {
            Result.Error(NetworkError.SERVER_UNAVAILABLE)
        } catch (e: SocketTimeoutException) {
            Result.Error(NetworkError.REQUEST_TIMEOUT)
        } catch (e: IOException) {
            Result.Error(NetworkError.NO_INTERNET)
        } catch (e: Exception) {
            Result.Error(NetworkError.UNKNOWN)
        }
    }
}
