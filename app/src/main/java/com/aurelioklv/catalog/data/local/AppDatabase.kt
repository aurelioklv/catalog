package com.aurelioklv.catalog.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aurelioklv.catalog.data.local.entities.BreedEntity

@Database(entities = [BreedEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}