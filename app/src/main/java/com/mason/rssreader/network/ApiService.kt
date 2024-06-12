package com.mason.rssreader.network

import com.mason.rssreader.data.model.NewsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

/**
 * Interface for the API service that defines the method to fetch news.
 */
interface ApiService {
    /**
     * Fetches news from the given RSS URL.
     *
     * @param rssUrl The RSS URL to fetch news from.
     * @return The fetched NewsResponse.
     */
    suspend fun getNews(rssUrl: String): NewsResponse
}

/**
 * Implementation of the ApiService interface using Ktor HttpClient.
 *
 * @property client The HttpClient used to make network requests.
 */
class ApiServiceImpl(private val client: HttpClient) : ApiService {
    /**
     * Fetches news from the given RSS URL.
     *
     * @param rssUrl The RSS URL to fetch news from.
     * @return The fetched NewsResponse.
     */
    override suspend fun getNews(rssUrl: String): NewsResponse {
        // Make a GET request to the API endpoint with the provided RSS URL as a parameter
        val response: HttpResponse = client.get("https://api.rss2json.com/v1/api.json") {
            parameter("rss_url", rssUrl)
        }
        // Parse and return the response body as NewsResponse
        return response.body()
    }
}
