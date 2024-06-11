package com.mason.rssreader.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mason.rssreader.data.model.NewsResponse
import com.mason.rssreader.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.mason.rssreader.data.model.Result

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val _news = MutableStateFlow<Result<NewsResponse>>(Result.Loading)
    val news: StateFlow<Result<NewsResponse>> get() = _news

    init {
        fetchNews("http://www.abc.net.au/news/feed/51120/rss.xml")
    }

    public fun fetchNews(rssUrl: String) {
        viewModelScope.launch {
            _news.value = Result.Loading
            _news.value = newsRepository.fetchNews(rssUrl)
        }
    }
}