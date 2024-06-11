package com.mason.rssreader.ui.screen
import androidx.compose.runtime.Composable
import org.koin.androidx.viewmodel.ext.android.viewModel

@Composable
fun RssReaderApp(viewModel: NewsViewModel) {
    NewsScreen(viewModel)
}


