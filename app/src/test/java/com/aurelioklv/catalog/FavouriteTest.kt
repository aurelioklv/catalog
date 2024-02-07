package com.aurelioklv.catalog

import com.aurelioklv.catalog.data.network.CatApi
import com.aurelioklv.catalog.data.network.model.AddFavouriteRequest
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit

class FavouriteTest {
    private lateinit var catApi: CatApi
    private val baseUrl = "https://api.thecatapi.com/v1/"

    private val userId = "qwert"

    private val realImageIds =
        listOf("0XYvRd7oD", "p6x60nX6U", "8NdgktL3E", "cZHbIzC_l", "DBmIBhhyv")
    private val fakeImageIds = listOf("qweqweqwe", "asdasdasd", "zxczxczxc")

    private val favouriteCandidates = mutableListOf<String>()

    @Before
    fun setUp() {
        catApi = Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
            .create(CatApi::class.java)
        runBlocking {
            removeAllFavourite()
        }
    }

    @After
    fun tearDown() = runBlocking {
        removeAllFavourite()
    }

    private suspend fun removeAllFavourite() {
        val favs = try {
            catApi.getFavourites(subId = userId)
        } catch (e: HttpException) {
            println("Catch getFavourite e: $e")
            emptyList()
        }

        favs.forEach {
            catApi.removeFavourite(favId = it.id)
        }
    }

    @Test
    fun getFavourites_isEmpty() = runBlocking {
        val favs = try {
            catApi.getFavourites(subId = userId)
        } catch (e: HttpException) {
            println("Catch getFavourite e: $e")
            emptyList()
        }

        assertNotNull(favs)
        assertTrue(favs.isEmpty())
    }

    @Test
    fun deleteAllFavourite_favouriteEmpty() = runBlocking {
        removeAllFavourite()

        val favs = try {
            catApi.getFavourites(subId = userId)
        } catch (e: HttpException) {
            println("Catch getFavourite e: $e")
            emptyList()
        }

        assertTrue(favs.isEmpty())
    }

    @Test
    fun getFavouriteById_favouriteExist() = runBlocking {
        favouriteCandidates.add(realImageIds[1])

        favouriteCandidates.forEach {
            val request = AddFavouriteRequest(imageId = it, subId = userId)
            try {
                val response = catApi.addFavourite(request = request)
                assertNotNull(response)
            } catch (e: HttpException) {
                println("Catch e: $e")
            }
        }

        val favs = catApi.getFavourites(subId = userId)
        val favId = favs.first().id

        val fav = catApi.getFavouriteById(favId = favId)

        assertNotNull(favs)
        assertNotNull(fav)
        assertEquals(favs.first(), fav)
    }

    @Test
    fun getFavouriteOfNonExistingId_favouriteNotExist() = runBlocking {
        favouriteCandidates.add(realImageIds[1])

        favouriteCandidates.forEach {
            val request = AddFavouriteRequest(imageId = it, subId = userId)
            try {
                val response = catApi.addFavourite(request = request)
                assertNotNull(response)
            } catch (e: HttpException) {
                println("Catch e: $e")
            }
        }

        val favs = catApi.getFavourites(subId = userId)
        val favId = -favs.first().id

        val fav = try {
            catApi.getFavouriteById(favId = favId)
        } catch (e: HttpException) {
            println("Catch getFavouriteById e: $e")
            null
        }

        assertNotNull(favs)
        assertNull(fav)
        assertNotEquals(favs.first(), fav)
    }

    @Test
    fun addFavourite_favExist() = runBlocking {
        favouriteCandidates.add(realImageIds[1])

        favouriteCandidates.forEach {
            val request = AddFavouriteRequest(imageId = it, subId = userId)
            try {
                val response = catApi.addFavourite(request = request)
                assertNotNull(response)
            } catch (e: HttpException) {
                println("Catch e: $e")
            }
        }


        val favs = catApi.getFavourites(subId = userId)

        assertTrue(favs.isNotEmpty())
        assertEquals(1, favs.size)
    }

    @Test
    fun addDuplicateFavourite_fail() = runBlocking {
        favouriteCandidates.add(realImageIds[2])
        favouriteCandidates.add(realImageIds[2])

        favouriteCandidates.forEach {
            val request = AddFavouriteRequest(imageId = it, subId = userId)

            try {
                val response = catApi.addFavourite(request = request)
                assertNotNull(response)
            } catch (e: HttpException) {
                println("Catch addFavourite e: $e")
            }
        }

        val favs = try {
            catApi.getFavourites(subId = userId)
        } catch (e: HttpException) {
            println("Catch getFavourite e: $e")
            emptyList()
        }

        assertNotNull(favs)
        assertTrue(favs.isNotEmpty())
        assertEquals(1, favs.size)
    }

    @Test
    fun addFavouriteToNonExistingImage_imageNotExistInFavourite() = runBlocking {
        favouriteCandidates.add(fakeImageIds[0])

        val request = AddFavouriteRequest(imageId = favouriteCandidates[0], subId = userId)
        try {
            catApi.addFavourite(request = request)
        } catch (e: HttpException) {
            println("Catch addFavourite e: $e")
        }

        val favs = try {
            catApi.getFavourites(subId = userId)
        } catch (e: HttpException) {
            println("Catch getFavourite e: $e")
            emptyList()
        }

        assertEquals(1, favs.size)
        println(Json.encodeToString(favs.first()))
        assertNotNull(favs.first().image)
    }

    @Test
    fun addFavouriteToExistingImage_imageExistInFavourite() = runBlocking {
        favouriteCandidates.add(realImageIds[0])

        val request = AddFavouriteRequest(imageId = favouriteCandidates[0], subId = userId)
        try {
            val response = catApi.addFavourite(request = request)
            assertNotNull(response)
        } catch (e: HttpException) {
            println("Catch addFavourite e: $e")
        }

        val favs = try {
            catApi.getFavourites(subId = userId)
        } catch (e: HttpException) {
            println("Catch getFavourite e: $e")
            emptyList()
        }

        assertTrue(favs.first().image != null)
        assertEquals(favs.first().imageId, favs.first().image!!.id)
    }

    @Test
    fun deleteFavourite_favouriteDeleted() = runBlocking {
        favouriteCandidates.addAll(realImageIds)

        favouriteCandidates.forEach {
            val request = AddFavouriteRequest(imageId = it, subId = userId)

            try {
                val response = catApi.addFavourite(request = request)
                assertNotNull(response)
            } catch (e: HttpException) {
                println("Catch addFavourite e: $e")
            }
        }

        val favs = try {
            catApi.getFavourites(subId = userId)
        } catch (e: HttpException) {
            println("Catch getFavourite e: $e")
            emptyList()
        }

        assertEquals(favouriteCandidates.size, favs.size)

        val deletedIndex = 2
        val deletedFav = favs[deletedIndex]
        val deletedFavId = deletedFav.id

        favouriteCandidates.removeAt(2)

        val response = catApi.removeFavourite(favId = deletedFavId)

        assertNotNull(response)
        assertEquals("SUCCESS", response.message)

        val favsAfter = try {
            catApi.getFavourites(subId = userId)
        } catch (e: HttpException) {
            println("Catch getFavourite e: $e")
            emptyList()
        }

        assertEquals(favouriteCandidates.size, favsAfter.size)
        assertFalse(favsAfter.contains(deletedFav))
    }
}