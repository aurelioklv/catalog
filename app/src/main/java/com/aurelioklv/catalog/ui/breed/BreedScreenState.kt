package com.aurelioklv.catalog.ui.breed

import com.aurelioklv.catalog.data.model.Breed
import com.aurelioklv.catalog.data.model.Cat

data class BreedScreenState(
    val breeds: List<Breed> = emptyList(),
    val currentBreed: Breed? = null,
    val currentBreedCatRef: Cat? = null,
    val isShowingDetails: Boolean = false,
    val isLoading: Boolean = true,
    val isError: Boolean = true
)