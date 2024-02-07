package com.aurelioklv.catalog.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed")
data class BreedEntity(
    val weightImperial: String,
    val weightMetric: String,
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo("cfa_url") val cfaUrl: String?,
    @ColumnInfo("vetstreet_url") val vetstreetUrl: String?,
    @ColumnInfo("vcahospitals_url") val vcahospitalsUrl: String?,
    val temperament: String,
    val origin: String,
    @ColumnInfo("country_codes") val countryCodes: String,
    @ColumnInfo("country_code") val countryCode: String,
    val description: String,
    @ColumnInfo("life_span") val lifeSpan: String,
    val indoor: Int,
    val lap: Int?,
    @ColumnInfo("alt_names") val altNames: String?,
    val adaptability: Int,
    @ColumnInfo("affection_level") val affectionLevel: Int,
    @ColumnInfo("child_friendly") val childFriendly: Int,
    @ColumnInfo("cat_friendly") val catFriendly: Int?,
    @ColumnInfo("dog_friendly") val dogFriendly: Int,
    @ColumnInfo("energy_level") val energyLevel: Int,
    val grooming: Int,
    @ColumnInfo("health_issues") val healthIssues: Int,
    val intelligence: Int,
    @ColumnInfo("shedding_level") val sheddingLevel: Int,
    @ColumnInfo("social_needs") val socialNeeds: Int,
    @ColumnInfo("stranger_friendly") val strangerFriendly: Int,
    val vocalisation: Int,
    val bidability: Int?,
    val experimental: Int,
    val hairless: Int,
    val natural: Int,
    val rare: Int,
    val rex: Int,
    @ColumnInfo("suppressed_tail") val suppressedTail: Int,
    @ColumnInfo("short_legs") val shortLegs: Int,
    @ColumnInfo("wikipedia_url") val wikipediaUrl: String?,
    val hypoallergenic: Int,
    @ColumnInfo("reference_image_id") val referenceImageId: String?
)