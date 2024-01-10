package com.aurelioklv.catalog.data.repository

import com.aurelioklv.catalog.data.api.CatsApiService
import com.aurelioklv.catalog.data.model.Cat

interface CatsRepository {
    suspend fun getCats(): List<Cat>
}

class DefaultCatsRepository(private val catsApiService: CatsApiService) : CatsRepository {
    override suspend fun getCats(): List<Cat> {
        return catsApiService.getCats()
    }
}