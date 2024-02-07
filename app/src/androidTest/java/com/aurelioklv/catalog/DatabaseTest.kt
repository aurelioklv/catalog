package com.aurelioklv.catalog

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aurelioklv.catalog.data.local.AppDao
import com.aurelioklv.catalog.data.local.AppDatabase
import com.aurelioklv.catalog.data.mapper.asEntity
import com.aurelioklv.catalog.data.network.fake.FakeDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var appDao: AppDao
    private lateinit var appDatabase: AppDatabase

    private val networkBreed = FakeDataSource.networkBreed

    private val entity = networkBreed.asEntity()
    private val entities = listOf(
        networkBreed.copy(id = "def", name = "DEF", countryCode = "YZ").asEntity(),
        networkBreed.copy(id = "ghi", name = "GHI", countryCode = "YZ").asEntity(),
        networkBreed.copy(id = "abc", name = "ABC", countryCode = "XY").asEntity(),
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        appDao = appDatabase.appDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun getEmptyBreed_noBreed() = runBlocking {
        val breeds = appDao.getBreedsAlphabetically().first()

        assertEquals(0, breeds.size)
        assertTrue(breeds.isEmpty())
    }

    @Test
    fun insertBreed_breedInserted() = runBlocking {
        insertBreed()
        val breed = appDao.getBreedById(networkBreed.id).first()

        assertEquals(entity.id, breed.id)
    }

    @Test
    fun insertMultipleBreeds_breedsInserted() = runBlocking {
        val size = insertMultipleBreeds()
        val breeds = appDao.getBreedsAlphabetically().first()

        assertEquals(size, breeds.size)
        breeds.forEachIndexed { index, breedEntity ->
            if (breedEntity != breeds.last()) {
                assertTrue(breedEntity.name < breeds[index + 1].name)
            }
        }
    }

    @Test
    fun insertDuplicate() = runBlocking {
        insertBreed()
        insertBreed()
        insertBreed()

        val breeds = appDao.getBreedsAlphabetically().first()
        assertEquals(1, breeds.size)
        assertEquals(networkBreed.id, breeds.first().id)
    }

    @Test
    fun updateBreed_breedUpdated() = runBlocking {
        insertBreed()
        appDao.updateBreed(networkBreed.asEntity().copy(weightMetric = "3 - 5"))
        val updatedBreed = appDao.getBreedById(networkBreed.id).first()

        assertEquals("3 - 5", updatedBreed.weightMetric)
    }

    @Test
    fun deletebBreed_breedDeleted() = runBlocking {
        val size = insertMultipleBreeds()
        appDao.deleteBreed(entities[1])
        var breeds = appDao.getBreedsAlphabetically().first()

        assertEquals(size - 1, breeds.size)

        appDao.deleteBreedById(entities[0].id)
        breeds = appDao.getBreedsAlphabetically().first()
        assertEquals(size - 2, breeds.size)
    }

    @Test
    fun deleteAllBreed_breedDeleted() = runBlocking {
        insertMultipleBreeds()
        appDao.deleteAllBreed()

        val breeds = appDao.getBreedsAlphabetically().first()

        assertTrue(breeds.isEmpty())
    }

    @Test
    fun getBreedByRegion_correctBreedReturned() = runBlocking {
        insertMultipleBreeds()

        var breeds = appDao.getBreedByRegion(listOf("AB")).first()
        assertTrue(breeds.isEmpty())

        breeds = appDao.getBreedByRegion(listOf("YZ")).first()
        assertTrue(breeds.isNotEmpty())
        assertEquals(2, breeds.size)

        breeds = appDao.getBreedByRegion(listOf("XY")).first()
        assertTrue(breeds.isNotEmpty())
        assertEquals(1, breeds.size)
    }

    private suspend fun insertBreed() {
        appDao.insertBreed(entity)
    }

    private suspend fun insertMultipleBreeds(): Int {
        appDao.insertBreed(entities)
        return entities.size
    }
}