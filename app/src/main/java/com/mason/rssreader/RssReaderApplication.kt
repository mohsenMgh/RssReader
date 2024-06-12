package com.mason.rssreader

import android.app.Application
import com.mason.rssreader.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Custom Application class for initializing Koin for dependency injection.
 */
class RssReaderApplication : Application() {
    /**
     * Called when the application is starting, before any other application objects have been created.
     * Here we initialize Koin with the necessary modules.
     */
    override fun onCreate() {
        super.onCreate()
        // Start Koin for dependency injection
        startKoin {
            // Use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()
            // Use the Android context
            androidContext(this@RssReaderApplication)
            // Load modules
            modules(appModule)
        }
    }
}
