package com.aurelioklv.catalog.ui.home

import com.aurelioklv.catalog.data.network.model.NetworkCat

sealed interface HomeScreenEvent {
    data class ShowCatDetails(val networkCat: NetworkCat) : HomeScreenEvent
    data object HideCatDetails : HomeScreenEvent
    data class SaveCatImage(val networkCat: NetworkCat) : HomeScreenEvent
    data object RefreshImage : HomeScreenEvent
}