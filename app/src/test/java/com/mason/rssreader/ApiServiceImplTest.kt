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

/**
 * Test class for ApiServiceImpl.
 * It uses a mock HttpClient to simulate network responses for testing.
 */
class ApiServiceImplTest {

    // Create a mock HttpClient with a predefined response
    private val mockHttpClient = HttpClient(MockEngine) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        engine {
            addHandler { request ->
                respond(
                    content = """{
                        "status": "ok",
                        "feed": {
                            "url": "http://www.abc.net.au/news/feed/51120/rss.xml",
                            "title": "ABC News",
                            "link": "http://www.abc.net.au/news",
                            "author": "ABC",
                            "description": "Latest news from ABC",
                            "image": "http://www.abc.net.au/image.jpg"
                        },
                        "items": [{
                            "title": "News title",
                            "pubDate": "2024-06-11",
                            "link": "http://www.abc.net.au/news/story",
                            "guid": "12345",
                            "author": "Author Name",
                            "thumbnail": "http://www.abc.net.au/thumbnail.jpg",
                            "description": "News Description",
                            "content": "News Content",
                            "enclosure": {
                                "link": "http://www.abc.net.au/enclosure",
                                "type": "image/jpeg",
                                "thumbnail": "http://www.abc.net.au/enclosure-thumbnail.jpg"
                            },
                            "categories": ["Category1", "Category2"]
                        }]
                    }""",
                    status = HttpStatusCode.OK,
                    headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                )
            }
        }
    }

    // Instance of the ApiServiceImpl using the mock HttpClient
    private val apiService = ApiServiceImpl(mockHttpClient)

    /**
     * Test that the getNews method returns a NewsResponse with the expected data.
     */
    @Test
    fun `test getNews returns NewsResponse`() = runBlocking {
        val rssUrl = "http://www.abc.net.au/news/feed/51120/rss.xml"
        val response = apiService.getNews(rssUrl)
        assertEquals("ok", response.status)
        assertEquals(1, response.items.size)
        assertEquals("News title", response.items[0].title)
    }
}
