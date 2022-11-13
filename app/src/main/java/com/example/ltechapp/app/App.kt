package com.example.ltechapp.app

import android.app.Application
import com.example.ltechapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    loginScreenModule,
                    sharedPrefModule,
                    apiModule,
                    newsScreenModule,
                    useCasesModule
                )
            )
        }
        super.onCreate()
    }
}