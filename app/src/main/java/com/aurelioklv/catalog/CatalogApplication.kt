package com.aurelioklv.catalog

import android.app.Application
import com.aurelioklv.catalog.di.AppContainer
import com.aurelioklv.catalog.di.DefaultAppContainer

class CatalogApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}