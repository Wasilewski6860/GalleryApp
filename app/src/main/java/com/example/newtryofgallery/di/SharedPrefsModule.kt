package com.example.newtryofgallery.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

//val sharedPrefsModule = module {
//
//    single{
//        getSharedPrefs(androidApplication())
//    }
//
//    single<SharedPreferences.Editor> {
//        getSharedPrefs(androidApplication()).edit()
//    }
//}
//
//fun getSharedPrefs(androidApplication: Application): SharedPreferences {
//    return  androidApplication.getSharedPreferences(SHARED_PREFERENCES_NAME,  android.content.Context.MODE_PRIVATE)
//}
//
const val SHARED_PREFERENCES_NAME = "sharedPref"




val sharedPrefsModule = module {
    single {
        provideSettingsPreferences(androidApplication())
    }
}


private fun provideSettingsPreferences(app: Application): SharedPreferences =
    app.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)