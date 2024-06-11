package com.mason.rssreader.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mason.rssreader.data.model.Item
import com.mason.rssreader.data.model.NewsResponse
import com.mason.rssreader.data.model.Result

@Composable
fun NewsScreen(viewModel: NewsViewModel) {
    val newsState by viewModel.news.collectAsState()

    when (newsState) {
        is Result.Loading -> {
            CircularProgressIndicator()
        }
        is Result.Failed -> {
            Text(text = "Error: ${(newsState as Result.Failed).error.message}")
        }
        is Result.Success -> {
            val newsResponse = (newsState as Result.Success<NewsResponse>).data
            LazyColumn {
                items(newsResponse.items) { item ->
                    NewsItem(item)
                }
            }
        }
    }
}

@Composable
fun NewsItem(newsItem: Item) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = newsItem.title, fontSize = 20.sp)
        Text(text = newsItem.description ?: "No Desc", fontSize = 16.sp)
        Text(text = newsItem.pubDate ?: "Not Specified", fontSize = 12.sp)
    }
}
