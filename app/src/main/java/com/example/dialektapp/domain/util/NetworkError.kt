package com.example.dialektapp.domain.util

enum class NetworkError: Error {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_UNAVAILABLE,
    SERVER_ERROR,
    SERIALIZATION_ERROR,
    UNKNOWN,
    NONE
}