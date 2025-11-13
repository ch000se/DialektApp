package com.example.dialektapp.data.remote

import com.example.dialektapp.domain.util.NetworkError
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.example.dialektapp.domain.util.Result

suspend inline fun <reified T> safeCall(apiCall: suspend () -> Response<T>): Result<T, NetworkError> {
    return try {
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
