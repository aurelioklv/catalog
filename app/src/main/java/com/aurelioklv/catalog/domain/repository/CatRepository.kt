package com.aurelioklv.catalog.domain.repository

import com.aurelioklv.catalog.data.api.ApiConstants.DEFAULT_HAS_BREEDS
import com.aurelioklv.catalog.data.api.ApiConstants.DEFAULT_LIMIT
import com.aurelioklv.catalog.data.model.Breed
import com.aurelioklv.catalog.data.model.Cat

interface CatRepository {
    suspend fun getCats(limit: Int = DEFAULT_LIMIT, hasBreeds: Int = DEFAULT_HAS_BREEDS): List<Cat>
    suspend fun getBreeds(): List<Breed>
}