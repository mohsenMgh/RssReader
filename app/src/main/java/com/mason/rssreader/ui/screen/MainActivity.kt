package com.mason.rssreader.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mason.rssreader.ui.theme.RssReaderTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        startKoin {
//            androidContext(this@MainActivity)
//            modules(appModule)
//        }
        setContent {
            RssReaderTheme {
                val viewModel: NewsViewModel by viewModel()
                RssReaderApp(viewModel)
            }
        }
    }


}
