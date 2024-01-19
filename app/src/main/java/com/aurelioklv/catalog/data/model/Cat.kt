package com.aurelioklv.catalog.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cat(
    val breeds: List<Breed>? = null,
    val id: String,

    @SerialName("url")
    val imageUrl: String,

    @SerialName("width")
    val imageWidth: Int,

    @SerialName("height")
    val imageHeight: Int
)