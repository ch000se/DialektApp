package com.example.dialektapp.presentation.util

import android.content.Context
import com.example.dialektapp.R
import com.example.dialektapp.domain.util.NetworkError

fun NetworkError.toUserMessage(context: Context): String {
    val resId = when (this) {
        NetworkError.NO_INTERNET -> R.string.no_internet
        NetworkError.REQUEST_TIMEOUT -> R.string.request_timeout
        NetworkError.SERVER_ERROR -> R.string.unknown
        NetworkError.TOO_MANY_REQUESTS -> R.string.too_many_requests
        NetworkError.SERIALIZATION_ERROR -> R.string.unknown
        NetworkError.UNKNOWN -> R.string.unknown
        NetworkError.NONE -> R.string.none_error
    }

    return context.getString(resId)
}