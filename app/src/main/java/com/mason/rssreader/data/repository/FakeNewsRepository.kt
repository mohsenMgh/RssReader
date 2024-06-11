package com.mason.rssreader.data.repository

import com.mason.rssreader.data.model.NewsResponse
import com.mason.rssreader.data.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeNewsRepository : NewsRepository {
    private val _newsResult = MutableStateFlow<Result<NewsResponse>>(Result.Loading)
    val newsResult: StateFlow<Result<NewsResponse>> get() = _newsResult

    fun setSuccessResponse(response: NewsResponse) {
        _newsResult.value = Result.Success(response)
    }

    fun setFailureResponse(exception: Exception) {
        _newsResult.value = Result.Failed(exception)
    }

    override suspend fun fetchNews(rssUrl: String): Result<NewsResponse> {
        return _newsResult.value
    }
}
