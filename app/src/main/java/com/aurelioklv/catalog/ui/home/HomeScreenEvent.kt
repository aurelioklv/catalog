package com.aurelioklv.catalog.ui.home

import com.aurelioklv.catalog.data.model.Cat

sealed interface HomeScreenEvent {
    data class ShowCatDetails(val cat: Cat) : HomeScreenEvent
    data object HideCatDetails : HomeScreenEvent
    data class SaveCatImage(val cat: Cat) : HomeScreenEvent
    data object RefreshImage : HomeScreenEvent
}