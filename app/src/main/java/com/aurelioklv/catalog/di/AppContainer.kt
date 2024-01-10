package com.aurelioklv.catalog.di

import com.aurelioklv.catalog.data.api.CatsApiService
import com.aurelioklv.catalog.data.repository.CatsRepository
import com.aurelioklv.catalog.data.repository.DefaultCatsRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val catsRepository: CatsRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.thecatapi.com/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: CatsApiService by lazy {
        retrofit.create(CatsApiService::class.java)
    }

    override val catsRepository: CatsRepository by lazy {
        DefaultCatsRepository(retrofitService)
    }
}