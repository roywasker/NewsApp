package com.roy.newsapp.data.remote

sealed class NetResult<T> {
    data class Success<T>(val response:T): NetResult<T>()
    data class NetworkFailure<T>(val networkError:Exception): NetResult<T>()
    data class Failure<T>(val generalError:Exception): NetResult<T>()

}
