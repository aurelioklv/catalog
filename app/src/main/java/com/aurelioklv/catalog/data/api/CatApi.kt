package com.aurelioklv.catalog.data.api

import com.aurelioklv.catalog.data.api.ApiConstants.API_KEY
import com.aurelioklv.catalog.data.api.ApiConstants.DEFAULT_HAS_BREEDS
import com.aurelioklv.catalog.data.api.ApiConstants.DEFAULT_LIMIT
import com.aurelioklv.catalog.data.model.Breed
import com.aurelioklv.catalog.data.model.Cat
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CatApi {
    @GET("images/search")
    suspend fun getCats(
        @Header("x-api-key") apiKey: String = API_KEY,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("has_breeds") hasBreeds: Int = DEFAULT_HAS_BREEDS
    ): List<Cat>

    @GET("breeds")
    suspend fun getBreeds(): List<Breed>
}