package com.aurelioklv.catalog.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Favourite(
    val id: Long,
    @SerialName("user_id") val userId: String,
    @SerialName("image_id") val imageId: String,
    @SerialName("sub_id") val subId: String,
    @SerialName("created_at") val createdAt: String,
    val image: Image?
)

@Serializable
data class AddFavouriteRequest(
    @SerialName("image_id") val imageId: String,
    @SerialName("sub_id") val subId: String,
)

@Serializable
data class AddFavouriteResponse(
    val message: String,
    val id: Long,
)

@Serializable
data class RemoveFavouriteResponse(
    val message: String
)