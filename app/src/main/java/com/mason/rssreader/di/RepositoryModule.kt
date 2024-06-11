package com.mason.rssreader.di

import com.mason.rssreader.data.repository.NewsRepository
import com.mason.rssreader.data.repository.NewsRepositoryImpl
import com.mason.rssreader.network.ApiService
import org.koin.dsl.module

val repositoryModule = module {
    single { provideRemoteRepository(get()) }
}

fun provideRemoteRepository(apiService: ApiService): NewsRepository {
    return NewsRepositoryImpl(apiService)
}
