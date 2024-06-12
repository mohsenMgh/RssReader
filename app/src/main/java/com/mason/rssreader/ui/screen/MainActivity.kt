package com.mason.rssreader.ui.screen

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mason.rssreader.ui.theme.RssReaderTheme
import com.mason.rssreader.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * MainActivity is the entry point of the app.
 * It sets up the screen orientation, enables edge-to-edge display, and sets the content view using Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    // Lazy initialization of the NewsViewModel using Koin dependency injection
    private val viewModel: NewsViewModel by viewModel()

    /**
     * Called when the activity is starting.
     * This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge display
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) // Set the screen orientation to portrait
        setContent {
            // Set the content view using Jetpack Compose and the app's theme
            RssReaderTheme {
                RssReaderApp(viewModel)
            }
        }
    }

    /**
     * Called when the activity will start interacting with the user.
     * At this point, the activity is at the top of the activity stack, with user input going to it.
     */
    override fun onResume() {
        super.onResume()
        viewModel.fetchNews() // Refresh the news data when the activity is resumed
    }
}
