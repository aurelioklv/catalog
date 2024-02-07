package com.aurelioklv.catalog.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkBreed(
    val weight: Weight,
    val id: String,
    val name: String,
    @SerialName("cfa_url") val cfaUrl: String? = null,
    @SerialName("vetstreet_url") val vetstreetUrl: String? = null,
    @SerialName("vcahospitals_url") val vcahospitalsUrl: String? = null,
    val temperament: String,
    val origin: String,
    @SerialName("country_codes") val countryCodes: String,
    @SerialName("country_code") val countryCode: String,
    val description: String,
    @SerialName("life_span") val lifeSpan: String,
    val indoor: Int,
    val lap: Int? = null,
    @SerialName("alt_names") val altNames: String? = null,
    val adaptability: Int,
    @SerialName("affection_level") val affectionLevel: Int,
    @SerialName("child_friendly") val childFriendly: Int,
    @SerialName("cat_friendly") val catFriendly: Int? = null,
    @SerialName("dog_friendly") val dogFriendly: Int,
    @SerialName("energy_level") val energyLevel: Int,
    val grooming: Int,
    @SerialName("health_issues") val healthIssues: Int,
    val intelligence: Int,
    @SerialName("shedding_level") val sheddingLevel: Int,
    @SerialName("social_needs") val socialNeeds: Int,
    @SerialName("stranger_friendly") val strangerFriendly: Int,
    val vocalisation: Int,
    val bidability: Int? = null,
    val experimental: Int,
    val hairless: Int,
    val natural: Int,
    val rare: Int,
    val rex: Int,
    @SerialName("suppressed_tail") val suppressedTail: Int,
    @SerialName("short_legs") val shortLegs: Int,
    @SerialName("wikipedia_url") val wikipediaUrl: String? = null,
    val hypoallergenic: Int,
    @SerialName("reference_image_id") val referenceImageId: String? = null
)