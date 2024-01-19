package com.aurelioklv.catalog.domain.repository

import com.aurelioklv.catalog.data.model.Cat

interface CatRepository {
    suspend fun getCats(): List<Cat>
}