package com.mason.rssreader.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.mason.rssreader.viewmodel.NewsViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

/**
 * Composable function to display the news screen.
 *
 * @param viewModel The ViewModel used to fetch and hold news data.
 */
@Composable
fun NewsScreen(viewModel: NewsViewModel = viewModel()) {
    // Collect the current state of the news data
    val newsResult by viewModel.news.collectAsState()

    // Display the UI based on the state of the news data
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        when (newsResult) {
            is Result.Loading -> {
                // Show a loading indicator while fetching data
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is Result.Success -> {
                // Show the list of articles if data fetching is successful
                val news = (newsResult as Result.Success<NewsResponse>).data
                ArticleList(articles = news.items)
            }
            is Result.Failed -> {
                // Show an error message if data fetching failed
                Text(
                    text = "Failed to load articles",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

/**
 * Composable function to display an individual article item.
 *
 * @param article The article data to display.
 * @param isTopArticle Whether this article is the top article.
 */
@Composable
fun ArticleItem(article: Item, isTopArticle: Boolean) {
    if (isTopArticle) {
        // Layout for the top article
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
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 8.dp, bottom = 8.dp)
                )
            }
        }
    } else {
        // Layout for regular articles
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Color.White) // Set the background color to white
                .height(130.dp)
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
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3

                    )
                    Text(
                        text = formatDate(article.pubDate),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 8.dp, bottom = 8.dp)
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

/**
 * Formats a date string to the desired format.
 *
 * @param dateString The date string to format.
 * @return The formatted date string or the original if parsing fails.
 */
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
            // Return the original date string if parsing fails
            dateString
        }
    } else {
        ""
    }
}

/**
 * Composable function to display a list of articles.
 *
 * @param articles The list of articles to display.
 */
@Composable
fun ArticleList(articles: List<Item>) {
    LazyColumn {
        itemsIndexed(articles) { index, article ->
            ArticleItem(article = article, isTopArticle = (index == 0))
        }
    }
}
