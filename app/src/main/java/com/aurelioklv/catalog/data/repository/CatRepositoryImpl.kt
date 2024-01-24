package com.aurelioklv.catalog.data.repository

import com.aurelioklv.catalog.data.api.CatApi
import com.aurelioklv.catalog.data.model.Breed
import com.aurelioklv.catalog.data.model.Cat
import com.aurelioklv.catalog.domain.repository.CatRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catApi: CatApi
) : CatRepository {
    override suspend fun getCats(limit: Int, hasBreeds: Int): List<Cat> {
        return catApi.getCats(limit = limit, hasBreeds = hasBreeds)
    }

    override suspend fun getCatById(id: String): Cat {
        return catApi.getCatById(id)
    }

    override suspend fun getBreeds(): List<Breed> {
        return catApi.getBreeds()
    }

    override suspend fun getBreedById(id: String): Breed {
        return catApi.getBreedById(id)
    }
}
