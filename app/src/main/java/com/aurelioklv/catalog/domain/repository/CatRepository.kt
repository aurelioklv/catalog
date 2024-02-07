package com.aurelioklv.catalog.domain.repository

import com.aurelioklv.catalog.data.network.ApiConstants.DEFAULT_HAS_BREEDS
import com.aurelioklv.catalog.data.network.ApiConstants.DEFAULT_LIMIT
import com.aurelioklv.catalog.data.network.model.NetworkBreed
import com.aurelioklv.catalog.data.network.model.NetworkCat

interface CatRepository {
    suspend fun getCats(
        limit: Int = DEFAULT_LIMIT,
        hasBreeds: Int = DEFAULT_HAS_BREEDS
    ): List<NetworkCat>

    suspend fun getCatById(id: String): NetworkCat
    suspend fun getNetworkBreeds(): List<NetworkBreed>

    suspend fun getBreedById(id: String): NetworkBreed
}