package com.aurelioklv.catalog.data.repository

import com.aurelioklv.catalog.data.api.CatApi
import com.aurelioklv.catalog.data.model.Cat
import com.aurelioklv.catalog.domain.repository.CatRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catApi: CatApi
) : CatRepository {
    override suspend fun getCats(): List<Cat> {
        return catApi.getCats()
    }
}
