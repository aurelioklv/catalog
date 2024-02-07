package com.aurelioklv.catalog

import com.aurelioklv.catalog.data.network.CatApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.create
import java.io.File

class NetworkCatApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var catApi: CatApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

        catApi = retrofit.create()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getBreeds() = runBlocking {
        val responseJson = readJsonFromFile("src/test/resources/breeds.json")
        val response = MockResponse().setBody(responseJson)

        mockWebServer.enqueue(response)

        val breeds = catApi.getBreeds()

        assertNotNull(responseJson)
        assertNotNull(breeds)
        assertEquals("abys", breeds.first().id)
    }

    @Test
    fun getCatWithBreed() = runBlocking {
        val responseJson = readJsonFromFile("src/test/resources/cat_with_breeds.json")
        val response = MockResponse().setBody(responseJson)

        mockWebServer.enqueue(response)

        val catWithBreeds = catApi.getCats()
        val expectedBreeds = listOf("chee", "ragd", "siam", "snow", "sphy")

        assertEquals(5, catWithBreeds.size)
        catWithBreeds.forEachIndexed { index, cat ->
            assertNotNull(cat.networkBreeds)
            assertEquals(expectedBreeds[index], cat.networkBreeds!!.first().id)
        }
    }

    private fun readJsonFromFile(filePath: String): String {
        val file = File(filePath)
        return file.readText()
    }
}