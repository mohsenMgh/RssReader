package com.mason.rssreader.data.model

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failed(val error: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}