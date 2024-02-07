package com.aurelioklv.catalog.data.network.fake

import com.aurelioklv.catalog.data.mapper.asEntity
import com.aurelioklv.catalog.data.mapper.asExternalModel
import com.aurelioklv.catalog.data.network.model.NetworkBreed
import com.aurelioklv.catalog.data.network.model.NetworkCat
import com.aurelioklv.catalog.data.network.model.Weight

object FakeDataSource {
    val networkBreed = NetworkBreed(
        weight = Weight(
            imperial = "7  -  10",
            metric = "3 - 5"
        ),
        id = "abys",
        name = "Abyssinian",
        cfaUrl = "http://cfa.org/Breeds/BreedsAB/Abyssinian.aspx",
        vetstreetUrl = "http://www.vetstreet.com/cats/abyssinian",
        vcahospitalsUrl = "https://vcahospitals.com/know-your-pet/cat-breeds/abyssinian",
        temperament = "Active, Energetic, Independent, Intelligent, Gentle",
        origin = "Egypt",
        countryCodes = "EG",
        countryCode = "EG",
        description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
        lifeSpan = "14 - 15",
        indoor = 0,
        lap = 1,
        altNames = "",
        adaptability = 5,
        affectionLevel = 5,
        childFriendly = 3,
        dogFriendly = 4,
        energyLevel = 5,
        grooming = 1,
        healthIssues = 2,
        intelligence = 5,
        sheddingLevel = 2,
        socialNeeds = 5,
        strangerFriendly = 5,
        vocalisation = 1,
        experimental = 0,
        hairless = 0,
        natural = 1,
        rare = 0,
        rex = 0,
        suppressedTail = 0,
        shortLegs = 0,
        wikipediaUrl = "https://en.wikipedia.org/wiki/Abyssinian_(cat)",
        hypoallergenic = 0,
        referenceImageId = "0XYvRd7oD"
    )

    val networkCat = NetworkCat(
        networkBreeds = listOf(networkBreed),
        id = "0XYvRd7oD",
        imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
        imageWidth = 1204,
        imageHeight = 1445
    )

    val networkBreeds: List<NetworkBreed> =
        MutableList(5) { networkBreed.copy(id = "${networkBreed.id}-$it") }
    val breeds = networkBreeds.map { it.asEntity().asExternalModel() }
}