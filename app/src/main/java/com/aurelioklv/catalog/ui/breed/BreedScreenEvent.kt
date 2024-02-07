package com.aurelioklv.catalog.ui.breed

import com.aurelioklv.catalog.data.network.model.NetworkBreed

sealed interface BreedScreenEvent {
    data object GetBreeds : BreedScreenEvent
    data class GetBreed(val id: String) : BreedScreenEvent
    data class ShowBreedDetails(val networkBreed: NetworkBreed) : BreedScreenEvent
    data object HideBreedDetails : BreedScreenEvent
}