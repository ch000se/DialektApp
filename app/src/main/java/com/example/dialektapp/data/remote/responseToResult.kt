package com.example.dialektapp.data.remote

import retrofit2.Response
import com.example.dialektapp.domain.util.Result
import com.example.dialektapp.domain.util.NetworkError

inline fun <reified T> responseToResult(response: Response<T>): Result<T, NetworkError> {
    return if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            Result.Success(body)
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
