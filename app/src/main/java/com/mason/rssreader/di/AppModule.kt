package com.mason.rssreader.di


import org.koin.dsl.module


val appModule = module {
    includes(viewModelModule, networkModule, repositoryModule)
}

