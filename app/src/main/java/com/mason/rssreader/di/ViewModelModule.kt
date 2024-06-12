package com.mason.rssreader.di

import com.mason.rssreader.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * ViewModel module providing dependency injection for ViewModels.
 */
val viewModelModule = module {
    // Provides a singleton instance of NewsViewModel
    viewModel { NewsViewModel(get()) }
}