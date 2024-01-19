package com.aurelioklv.catalog.di

import com.aurelioklv.catalog.data.repository.CatRepositoryImpl
import com.aurelioklv.catalog.domain.repository.CatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCatRepository(catRepositoryImpl: CatRepositoryImpl): CatRepository
}