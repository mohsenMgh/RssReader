package com.mason.rssreader

import com.mason.rssreader.network.ApiServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test

class ApiServiceImplTest {
    private val mockHttpClient = HttpClient(MockEngine) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        engine {
            addHandler { request ->
                respond(
                    content = """{"status":"ok","items":[{"title":"News title"}]}""",
                    status = HttpStatusCode.OK,
                    headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                )
            }
        }
    }

    private val apiService = ApiServiceImpl(mockHttpClient)

    @Test
    fun `test getNews returns NewsResponse`() = runBlocking {
        val rssUrl = "http://www.abc.net.au/news/feed/51120/rss.xml"
        val response = apiService.getNews(rssUrl)
        assertEquals("ok", response.status)
        assertEquals(1, response.items.size)
        assertEquals("News title", response.items[0].title)
    }
}
