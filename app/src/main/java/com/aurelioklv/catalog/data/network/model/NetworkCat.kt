package com.aurelioklv.catalog.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCat(
    @SerialName("breeds") val networkBreeds: List<NetworkBreed>? = null,
    val categories: List<Category>? = null,
    val id: String,

    @SerialName("url")
    val imageUrl: String,

    @SerialName("width")
    val imageWidth: Int,

    @SerialName("height")
    val imageHeight: Int
)