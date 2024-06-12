package com.mason.rssreader

import com.mason.rssreader.data.repository.NewsRepositoryImpl
import com.mason.rssreader.data.model.Enclosure
import com.mason.rssreader.data.model.Feed
import com.mason.rssreader.data.model.Item
import com.mason.rssreader.data.model.NewsResponse
import com.mason.rssreader.data.model.Result
import com.mason.rssreader.network.ApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit test for NewsRepositoryImpl.
 */
class NewsRepositoryImplTest {

    // Mock ApiService to simulate network responses
    private val mockApiService = mockk<ApiService>()
    // Instance of NewsRepositoryImpl using the mock ApiService
    private val newsRepository = NewsRepositoryImpl(mockApiService)

    /**
     * Tests if fetchNews() returns a Success result when the API call is successful.
     */
    @Test
    fun `test fetchNews returns Success result`() = runBlocking {
        val rssUrl = "http://www.abc.net.au/news/feed/51120/rss.xml"
        val mockResponse = NewsResponse(
            status = "ok",
            feed = Feed(
                url = "http://www.abc.net.au/news/feed/51120/rss.xml",
                title = "ABC News",
                link = "http://www.abc.net.au/news",
                author = "ABC",
                description = "Latest news from ABC",
                image = "http://www.abc.net.au/image.jpg"
            ),
            items = listOf(
                Item(
                    title = "News Title",
                    pubDate = "2024-06-11",
                    link = "http://www.abc.net.au/news/story",
                    guid = "12345",
                    author = "Author Name",
                    thumbnail = "http://www.abc.net.au/thumbnail.jpg",
                    description = "News Description",
                    content = "News Content",
                    enclosure = Enclosure(
                        link = "http://www.abc.net.au/enclosure",
                        type = "image/jpeg",
                        thumbnail = "http://www.abc.net.au/enclosure-thumbnail.jpg"
                    ),
                    categories = listOf("Category1", "Category2")
                )
            )
        )
        // Mock the ApiService response
        coEvery { mockApiService.getNews(rssUrl) } returns mockResponse

        // Fetch news and assert the result is Success
        val result = newsRepository.fetchNews(rssUrl)
        assertTrue(result is Result.Success)
        val newsResponse = (result as Result.Success).data
        assertEquals("ok", newsResponse.status)
        assertEquals("ABC News", newsResponse.feed?.title)
        assertEquals(1, newsResponse.items.size)
        assertEquals("News Title", newsResponse.items[0].title)
    }

    /**
     * Tests if fetchNews() returns a Failed result when the API call throws an exception.
     */
    @Test
    fun `test fetchNews returns Failed result on exception`() = runBlocking {
        val rssUrl = "http://www.abc.net.au/news/feed/51120/rss.xml"
        // Mock the ApiService to throw an exception
        coEvery { mockApiService.getNews(rssUrl) } throws Exception("Network Error")

        // Fetch news and assert the result is Failed
        val result = newsRepository.fetchNews(rssUrl)
        assertTrue(result is Result.Failed)
    }
}
