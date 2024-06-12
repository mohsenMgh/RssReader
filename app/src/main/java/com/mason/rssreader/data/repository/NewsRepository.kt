package com.mason.rssreader.data.repository

import com.mason.rssreader.data.model.NewsResponse
import com.mason.rssreader.data.model.Result
import com.mason.rssreader.network.ApiService

/**
 * Implementation of the NewsRepository interface.
 *
 * @property apiService The ApiService used to fetch news data.
 */
class NewsRepositoryImpl(private val apiService: ApiService) : NewsRepository {
    /**
     * Fetches news from the given RSS URL.
     *
     * @param rssUrl The RSS URL to fetch news from.
     * @return A Result containing either the fetched NewsResponse or an error.
     */
    override suspend fun fetchNews(rssUrl: String): Result<NewsResponse> {
        return try {
            // Try to fetch the news from the API service
            val response = apiService.getNews(rssUrl)
            // Return a successful result with the fetched news
            Result.Success(response)
        } catch (e: Exception) {
            // Return a failed result with the caught exception
            Result.Failed(e)
        }
    }
}

/**
 * Interface for a news repository.
 * Provides a method to fetch news from a given RSS URL.
 */
interface NewsRepository {
    /**
     * Fetches news from the given RSS URL.
     *
     * @param rssUrl The RSS URL to fetch news from.
     * @return A Result containing either the fetched NewsResponse or an error.
     */
    suspend fun fetchNews(rssUrl: String): Result<NewsResponse>
}
