package com.infoshell.presentation

import android.app.Application
import com.infoshell.book24.BuildConfig
import com.infoshell.data.di.networkModule
import com.infoshell.data.di.repositoryModule
import com.infoshell.domain.di.useCaseModule
import com.infoshell.presentation.di.recyclerViewAdaptersModule
import com.infoshell.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.infoshell.cache.di.storageModule
import timber.log.Timber
import timber.log.Timber.DebugTree

class BookApplication : Application() {

    private val appModules by lazy {
        listOf(
            networkModule,
            repositoryModule,
            useCaseModule,
            viewModelModule,
            storageModule,
            recyclerViewAdaptersModule
        )
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(DebugTree())

        startKoin {
            androidContext(this@BookApplication)
            modules(appModules)
        }
    }
}