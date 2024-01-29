package com.aurelioklv.catalog.data.api

import com.aurelioklv.catalog.data.api.ApiConstants.API_KEY
import com.aurelioklv.catalog.data.api.ApiConstants.DEFAULT_HAS_BREEDS
import com.aurelioklv.catalog.data.api.ApiConstants.DEFAULT_LIMIT
import com.aurelioklv.catalog.data.model.AddFavouriteRequest
import com.aurelioklv.catalog.data.model.AddFavouriteResponse
import com.aurelioklv.catalog.data.model.Breed
import com.aurelioklv.catalog.data.model.Cat
import com.aurelioklv.catalog.data.model.Favourite
import com.aurelioklv.catalog.data.model.RemoveFavouriteResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CatApi {
    @GET("images/search")
    suspend fun getCats(
        @Header("x-api-key") apiKey: String = API_KEY,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("has_breeds") hasBreeds: Int = DEFAULT_HAS_BREEDS
    ): List<Cat>

    @GET("images/{catId}")
    suspend fun getCatById(@Path("catId") catId: String): Cat

    @GET("breeds")
    suspend fun getBreeds(): List<Breed>

    @GET("breeds/{breedId}")
    suspend fun getBreedById(@Path("breedId") breedId: String): Breed

    @GET("favourites")
    suspend fun getFavourites(
        @Header("x-api-key") apiKey: String = API_KEY,
        @Query("sub_id") subId: String,
    ): List<Favourite>

    @POST("favourites")
    suspend fun addFavourite(
        @Header("x-api-key") apiKey: String = API_KEY,
        @Body request: AddFavouriteRequest,
    ): AddFavouriteResponse

    @DELETE("favourites/{favouriteId}")
    suspend fun removeFavourite(
        @Header("x-api-key") apiKey: String = API_KEY,
        @Path("favouriteId") favId: Long,
    ): RemoveFavouriteResponse
}