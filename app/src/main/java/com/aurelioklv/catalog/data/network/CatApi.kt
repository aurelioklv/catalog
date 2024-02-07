package com.aurelioklv.catalog.data.network

import com.aurelioklv.catalog.data.network.ApiConstants.API_KEY
import com.aurelioklv.catalog.data.network.ApiConstants.DEFAULT_HAS_BREEDS
import com.aurelioklv.catalog.data.network.ApiConstants.DEFAULT_LIMIT
import com.aurelioklv.catalog.data.network.model.AddFavouriteRequest
import com.aurelioklv.catalog.data.network.model.AddFavouriteResponse
import com.aurelioklv.catalog.data.network.model.Favourite
import com.aurelioklv.catalog.data.network.model.NetworkBreed
import com.aurelioklv.catalog.data.network.model.NetworkCat
import com.aurelioklv.catalog.data.network.model.RemoveFavouriteResponse
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
    ): List<NetworkCat>

    @GET("images/{catId}")
    suspend fun getCatById(@Path("catId") catId: String): NetworkCat

    @GET("breeds")
    suspend fun getBreeds(): List<NetworkBreed>

    @GET("breeds/{breedId}")
    suspend fun getBreedById(@Path("breedId") breedId: String): NetworkBreed

    @GET("favourites")
    suspend fun getFavourites(
        @Header("x-api-key") apiKey: String = API_KEY,
        @Query("sub_id") subId: String,
    ): List<Favourite>

    @GET("favourites/{favouriteId}")
    suspend fun getFavouriteById(
        @Header("x-api-key") apiKey: String = API_KEY,
        @Path("favouriteId") favId: Long
    ): Favourite

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