package com.mason.rssreader.network

import com.mason.rssreader.data.model.NewsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

interface ApiService {
    suspend fun getNews(rssUrl: String): NewsResponse
}

class ApiServiceImpl(private val client: HttpClient) : ApiService {
    override suspend fun getNews(rssUrl: String): NewsResponse {
        val response: HttpResponse = client.get("https://api.rss2json.com/v1/api.json") {
            parameter("rss_url", rssUrl)
        }
        return response.body()
    }
}
