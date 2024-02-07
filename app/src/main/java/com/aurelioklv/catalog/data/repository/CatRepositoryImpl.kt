package com.aurelioklv.catalog.data.repository

import com.aurelioklv.catalog.data.network.CatApi
import com.aurelioklv.catalog.data.network.model.NetworkBreed
import com.aurelioklv.catalog.data.network.model.NetworkCat
import com.aurelioklv.catalog.domain.repository.CatRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catApi: CatApi,
) : CatRepository {
    override suspend fun getCats(limit: Int, hasBreeds: Int): List<NetworkCat> {
        return catApi.getCats(limit = limit, hasBreeds = hasBreeds)
    }

    override suspend fun getCatById(id: String): NetworkCat {
        return catApi.getCatById(id)
    }

    override suspend fun getNetworkBreeds(): List<NetworkBreed> {
        return catApi.getBreeds()
    }

    override suspend fun getBreedById(id: String): NetworkBreed {
        return catApi.getBreedById(id)
    }
}
