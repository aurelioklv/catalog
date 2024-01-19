package com.aurelioklv.catalog.data.api

import com.aurelioklv.catalog.data.model.Breed
import com.aurelioklv.catalog.data.model.Cat
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    @GET("images/search")
    suspend fun getCats(
        @Query("limit") limit: Int = 10,
        @Query("has_breeds") hasBreed: Int = 0
    ): List<Cat>

    @GET("breeds")
    suspend fun getBreeds(
        @Query("limit") limit: Int? = null,
    ): List<Breed>
}