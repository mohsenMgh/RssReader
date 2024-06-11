package com.mason.rssreader.di

import io.ktor.client.HttpClient
import com.mason.rssreader.network.ApiService
import com.mason.rssreader.network.ApiServiceImpl
import com.mason.rssreader.network.httpClientAndroid
import org.koin.dsl.module

val networkModule = module {
    single { provideApiService(get()) }
    single { provideHttpClient() }
}

fun provideHttpClient(): HttpClient {
    return httpClientAndroid
}

fun provideApiService(httpClient: HttpClient): ApiService {
    return ApiServiceImpl(httpClient)
}
