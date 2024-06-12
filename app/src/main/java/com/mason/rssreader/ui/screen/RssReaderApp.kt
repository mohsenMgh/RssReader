package com.mason.rssreader.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mason.rssreader.viewmodel.NewsViewModel

/**
 * Composable function to set up the main application UI.
 *
 * @param viewModel The ViewModel used to fetch and hold news data.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RssReaderApp(viewModel: NewsViewModel) {
    val systemUiController = rememberSystemUiController()

    // Side effect to set the status bar color to black
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Black,
            darkIcons = false
        )
    }

    // Scaffold to set up the basic structure of the app with a top bar
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Title",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.secondary
                )
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                NewsScreen(viewModel)
            }
        }
    )
}
