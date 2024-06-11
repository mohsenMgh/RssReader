package com.mason.rssreader.data.repository

import com.mason.rssreader.data.model.NewsResponse
import com.mason.rssreader.data.model.Result
import com.mason.rssreader.network.ApiService

class NewsRepositoryImpl(private val apiService: ApiService) : NewsRepository{
    override suspend fun fetchNews(rssUrl: String): Result<NewsResponse> {
        return try {
            val response = apiService.getNews(rssUrl)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failed(e)
        }
    }
}
interface NewsRepository {
    suspend fun fetchNews(rssUrl: String): Result<NewsResponse>
}