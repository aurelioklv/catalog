package com.aurelioklv.catalog.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.aurelioklv.catalog.data.api.CatApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val baseUrl = "https://api.thecatapi.com/v1/"

    @Provides
    @Singleton
    fun provideCatApi(): CatApi {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
            .create(CatApi::class.java)
    }

    @Provides
    @Singleton
    fun providesImageLoader(@ApplicationContext application: Context): ImageLoader {
        return ImageLoader.Builder(application)
            .memoryCache {
                MemoryCache.Builder(application)
                    .maxSizePercent(0.5)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(application.cacheDir)
                    .maxSizeBytes(100_000_000)
                    .build()
            }
            .build()
    }
}