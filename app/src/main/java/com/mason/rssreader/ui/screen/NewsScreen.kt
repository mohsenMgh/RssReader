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
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mason.rssreader.data.model.Enclosure
import java.time.LocalDateTime

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale


@Composable
fun NewsScreen(viewModel: NewsViewModel = viewModel()) {
    val newsResult by viewModel.news.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray)
    ) {
        when (newsResult) {
            is Result.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is Result.Success -> {
                val news = (newsResult as Result.Success<NewsResponse>).data
                ArticleList(articles = news.items)
            }
            is Result.Failed -> {
                Text(
                    text = "Failed to load articles",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun ArticleItem(article: Item, isTopArticle: Boolean) {
    if (isTopArticle) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Color.White) // Set the background color to white
        ) {
            Image(
                painter = rememberImagePainter(
                    data = article.enclosure?.link,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(150.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3
                    )
                }
                Text(
                    text = formatDate(article.pubDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.BottomStart).padding(start = 8.dp, bottom = 8.dp)
                )
            }
        }
    } else {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Color.White) // Set the background color to white
                .height(150.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxHeight()) {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = formatDate(article.pubDate),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.BottomStart).padding(start = 8.dp, bottom = 8.dp)
                    )
                }
            }
            Image(
                painter = rememberImagePainter(
                    data = article.thumbnail,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp)
            )
        }
    }
}
fun formatDate(dateString: String?): String {
    return if (dateString != null) {
        try {
            // Define the input formatter based on the given date format
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            // Define the output formatter to match the desired format with uppercase AM/PM
            val outputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy  hh:mm a", Locale.ENGLISH)
            // Parse the date string
            val date = LocalDateTime.parse(dateString, inputFormatter)
            // Format the date and convert AM/PM to uppercase
            outputFormatter.format(date).uppercase(Locale.ENGLISH)
        } catch (e: DateTimeParseException) {
            // Log the exception
            Log.e("formatDate", "DateTimeParseException", e)
            // Return an empty string if parsing fails
            dateString
        }
    } else {
        ""
    }
}

@Composable
fun ArticleList(articles: List<Item>) {
    LazyColumn {
        itemsIndexed(articles) { index, article ->
            ArticleItem(article = article, isTopArticle = (index == 0))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewArticleList() {
    val sampleArticles = listOf(
        Item(
            title = "Top Article Title",
            pubDate = "2024-06-11 02:34:29",
            link = "http://www.sample.com",
            guid = "sample-guid",
            author = "Sample Author",
            thumbnail = "https://via.placeholder.com/150",
            description = "Sample description",
            content = "Sample content",
            enclosure = Enclosure(
                link = "https://via.placeholder.com/600",
                type = "image/jpeg",
                thumbnail = "https://via.placeholder.com/600"
            ),
            categories = listOf("Category1", "Category2")
        ),
        Item(
            title = "Regular Article Title",
            pubDate = "2024-06-11 02:34:29",
            link = "http://www.sample.com",
            guid = "sample-guid",
            author = "Sample Author",
            thumbnail = "https://via.placeholder.com/150",
            description = "Sample description",
            content = "Sample content",
            enclosure = null,
            categories = listOf("Category1", "Category2")
        )
    )


}

