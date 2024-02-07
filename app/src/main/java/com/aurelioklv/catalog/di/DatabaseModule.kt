package com.aurelioklv.catalog.di

import android.content.Context
import androidx.room.Room
import com.aurelioklv.catalog.data.local.AppDao
import com.aurelioklv.catalog.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "catalog_db")
            .build()
    }

    @Provides
    fun providesAppDao(database: AppDatabase): AppDao {
        return database.appDao()
    }
}