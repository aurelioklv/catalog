package com.aurelioklv.catalog.ui.breed

import com.aurelioklv.catalog.data.model.Breed

data class BreedScreenState(
    val breeds: List<Breed> = emptyList(),
    val currentBreed: Breed? = null,
    val isShowingDetails: Boolean = false,
    val isLoading: Boolean = true,
    val isError: Boolean = true
)