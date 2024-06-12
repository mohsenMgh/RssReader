package com.mason.rssreader.di

import org.koin.dsl.module

/**
 * Application module that includes all the necessary sub-modules.
 * This module includes the ViewModel, Network, and Repository modules.
 */
val appModule = module {
    includes(viewModelModule, networkModule, repositoryModule)
}
