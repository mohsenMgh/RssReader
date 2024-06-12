package com.mason.rssreader.di

import io.ktor.client.HttpClient
import com.mason.rssreader.network.ApiService
import com.mason.rssreader.network.ApiServiceImpl
import com.mason.rssreader.network.httpClientAndroid
import org.koin.dsl.module

/**
 * Network module providing dependency injection for network-related classes.
 */
val networkModule = module {
    // Provides a singleton instance of ApiService
    single { provideApiService(get()) }
    // Provides a singleton instance of HttpClient
    single { provideHttpClient() }
}

/**
 * Provides a configured instance of HttpClient.
 *
 * @return An instance of HttpClient configured with necessary features.
 */
fun provideHttpClient(): HttpClient {
    return httpClientAndroid
}

/**
 * Provides an instance of ApiService.
 *
 * @param httpClient The HttpClient used to make network requests.
 * @return An instance of ApiServiceImpl.
 */
fun provideApiService(httpClient: HttpClient): ApiService {
    return ApiServiceImpl(httpClient)
}
