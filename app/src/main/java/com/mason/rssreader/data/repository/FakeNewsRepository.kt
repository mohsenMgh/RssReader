package com.mason.rssreader.data.repository

import com.mason.rssreader.data.model.NewsResponse
import com.mason.rssreader.data.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * A fake implementation of the NewsRepository for testing purposes.
 * This class allows setting success or failure responses manually.
 */
class FakeNewsRepository : NewsRepository {
    // Holds the current state of the news result.
    private val _newsResult = MutableStateFlow<Result<NewsResponse>>(Result.Loading)
    val newsResult: StateFlow<Result<NewsResponse>> get() = _newsResult

    /**
     * Sets a successful response with the given NewsResponse.
     *
     * @param response The NewsResponse to be set as the success result.
     */
    fun setSuccessResponse(response: NewsResponse) {
        _newsResult.value = Result.Success(response)
    }

    /**
     * Sets a failure response with the given exception.
     *
     * @param exception The Exception to be set as the failure result.
     */
    fun setFailureResponse(exception: Exception) {
        _newsResult.value = Result.Failed(exception)
    }

    /**
     * Fetches the news from the given RSS URL.
     * In this fake implementation, it returns the current value of _newsResult.
     *
     * @param rssUrl The RSS URL to fetch news from.
     * @return The current value of _newsResult.
     */
    override suspend fun fetchNews(rssUrl: String): Result<NewsResponse> {
        return _newsResult.value
    }
}
