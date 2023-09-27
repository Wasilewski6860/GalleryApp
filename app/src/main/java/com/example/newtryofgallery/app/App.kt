package com.example.newtryofgallery.app

import android.app.Application
import com.example.newtryofgallery.di.dataModule
import com.example.newtryofgallery.di.domainModule
import com.example.newtryofgallery.di.presentationModule
import com.example.newtryofgallery.di.sharedPrefsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(listOf(dataModule, domainModule, presentationModule, sharedPrefsModule))
        }
    }
}