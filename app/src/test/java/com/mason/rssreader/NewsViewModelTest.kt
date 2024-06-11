package com.mason.rssreader

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.rules.TestRule
import com.mason.rssreader.ui.screen.NewsViewModel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mason.rssreader.data.model.*
import com.mason.rssreader.data.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*


@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val mockNewsRepository = mockk<NewsRepository>()
    private lateinit var viewModel: NewsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Setup the mock behavior before creating the ViewModel
        val rssUrl = "http://www.abc.net.au/news/feed/51120/rss.xml"
        val mockResponse = NewsResponse(
            status = "ok",
            feed = Feed(
                url = rssUrl,
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
        coEvery { mockNewsRepository.fetchNews(rssUrl) } returns Result.Success(mockResponse)

        viewModel = NewsViewModel(mockNewsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `test fetchNews updates state flow with success`() = runTest {
        val rssUrl = "http://www.abc.net.au/news/feed/51120/rss.xml"

        // Re-trigger fetchNews to test success case
        viewModel.fetchNews(rssUrl)

        advanceUntilIdle() // Ensure all coroutines complete

        assertTrue(viewModel.news.value is Result.Success)
        val newsResponse = (viewModel.news.value as Result.Success).data
        assertEquals("ok", newsResponse.status)
        assertEquals("ABC News", newsResponse.feed?.title)
        assertEquals(1, newsResponse.items.size)
        assertEquals("News Title", newsResponse.items[0].title)
    }

    @Test
    fun `test fetchNews updates state flow with failure`() = runTest {
        val rssUrl = "http://www.abc.net.au/news/feed/51120/rss.xml"
        coEvery { mockNewsRepository.fetchNews(rssUrl) } returns Result.Failed(Exception("Network Error"))

        // Re-trigger fetchNews to test failure case
        viewModel.fetchNews(rssUrl)

        advanceUntilIdle() // Ensure all coroutines complete

        assertTrue(viewModel.news.value is Result.Failed)
    }
}
