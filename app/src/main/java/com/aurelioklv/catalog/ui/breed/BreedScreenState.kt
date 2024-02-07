package com.aurelioklv.catalog.ui.breed

import com.aurelioklv.catalog.data.network.model.NetworkBreed
import com.aurelioklv.catalog.data.network.model.NetworkCat

data class BreedScreenState(
    val networkBreeds: List<NetworkBreed> = emptyList(),
    val currentNetworkBreed: NetworkBreed? = null,
    val currentBreedNetworkCatRef: NetworkCat? = null,
    val isShowingDetails: Boolean = false,
    val isLoading: Boolean = true,
    val isError: Boolean = true
)