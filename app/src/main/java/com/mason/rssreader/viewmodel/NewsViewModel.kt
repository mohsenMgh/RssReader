package com.mason.rssreader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mason.rssreader.data.model.NewsResponse
import com.mason.rssreader.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.mason.rssreader.data.model.Result

/**
 * ViewModel for managing and fetching news data.
 *
 * @property newsRepository The repository used to fetch news data.
 */
class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    // Holds the current state of the news data
    private val _news = MutableStateFlow<Result<NewsResponse>>(Result.Loading)
    val news: StateFlow<Result<NewsResponse>> get() = _news

    // Indicates whether a refresh operation is in progress
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    companion object {
        // The RSS URL to fetch news from
        const val rssUrl = "http://www.abc.net.au/news/feed/51120/rss.xml"
    }

    // Initialize the ViewModel by fetching the initial news data
    init {
        fetchNews(rssUrl)
    }

    /**
     * Fetches news from the given RSS URL.
     *
     * @param rssUrl The RSS URL to fetch news from.
     */
    fun fetchNews(rssUrl: String) {
        viewModelScope.launch {
            if (_news.value is Result.Success) {
                _isRefreshing.value = true
            } else {
                _news.value = Result.Loading
            }

            _news.value = newsRepository.fetchNews(rssUrl)
            _isRefreshing.value = false
        }
    }

    /**
     * Fetches news using the default RSS URL.
     */
    fun fetchNews() {
        fetchNews(rssUrl)
    }
}
