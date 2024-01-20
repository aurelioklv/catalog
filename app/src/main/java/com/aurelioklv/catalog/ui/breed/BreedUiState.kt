package com.aurelioklv.catalog.ui.breed

import com.aurelioklv.catalog.data.model.Breed

sealed interface BreedUiState {
    data class Success(val breeds: List<Breed>) : BreedUiState
    data object Error : BreedUiState
    data object Loading : BreedUiState
}