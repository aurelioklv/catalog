package com.aurelioklv.catalog.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Category(
    val id: Int,
    val name: String,
)

@Serializable
data class Weight(
    val imperial: String,
    val metric: String
)

@Serializable
data class Image(
    val id: String? = null,
    val url: String? = null,
)