package com.aurelioklv.catalog.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aurelioklv.catalog.data.local.entities.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBreed(breed: BreedEntity)

    @Insert
    suspend fun insertBreed(breeds: List<BreedEntity>)

    @Update
    suspend fun updateBreed(breed: BreedEntity)

    @Delete
    suspend fun deleteBreed(breed: BreedEntity)

    @Query("DELETE FROM breed")
    suspend fun deleteAllBreed()

    @Query("DELETE FROM breed WHERE id = :id")
    suspend fun deleteBreedById(id: String)

    @Query("SELECT * FROM breed WHERE id = :id")
    fun getBreedById(id: String): Flow<BreedEntity>

    @Query("SELECT * FROM breed ORDER BY name")
    fun getBreedsAlphabetically(): Flow<List<BreedEntity>>

    @Query("SELECT * FROM breed WHERE country_code IN (:regions)")
    fun getBreedByRegion(regions: List<String>): Flow<List<BreedEntity>>
}