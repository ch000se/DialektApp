package com.example.dialektapp.data.remote

import com.example.dialektapp.domain.util.NetworkError
import retrofit2.Response
import java.io.IOException
import com.example.dialektapp.domain.util.Result

suspend inline fun <reified T> safeCall(apiCall: suspend () -> Response<T>): Result<T, NetworkError> {
    return try {
        val response = apiCall()
        responseToResult(response)
    } catch (e: IOException) {
        Result.Error(NetworkError.NO_INTERNET)
    } catch (e: Exception) {
        Result.Error(NetworkError.UNKNOWN)
    }
}
