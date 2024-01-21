package com.aurelioklv.catalog.ui.breed

import com.aurelioklv.catalog.data.model.Breed

sealed interface BreedScreenEvent {
    data object GetBreeds : BreedScreenEvent
    data class ShowBreedDetails(val breed: Breed) : BreedScreenEvent
    data object HideBreedDetails : BreedScreenEvent
}