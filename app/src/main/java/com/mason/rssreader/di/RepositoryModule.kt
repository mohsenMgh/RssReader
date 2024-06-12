package com.mason.rssreader.di

import com.mason.rssreader.data.repository.NewsRepository
import com.mason.rssreader.data.repository.NewsRepositoryImpl
import com.mason.rssreader.network.ApiService
import org.koin.dsl.module

/**
 * Repository module providing dependency injection for repositories.
 */
val repositoryModule = module {
    // Provides a singleton instance of NewsRepository
    single { provideRemoteRepository(get()) }
}

/**
 * Provides an instance of NewsRepository.
 *
 * @param apiService The ApiService used to fetch news data.
 * @return An instance of NewsRepositoryImpl.
 */
fun provideRemoteRepository(apiService: ApiService): NewsRepository {
    return NewsRepositoryImpl(apiService)
}
