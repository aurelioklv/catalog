package com.aurelioklv.catalog.ui.home

import com.aurelioklv.catalog.data.model.Cat

data class HomeScreenState(
    val numberOfCat: Int? = null,
    val hasBreeds: Int = 1,
    val cats: List<Cat> = emptyList(),
    val isShowingDetails: Boolean = false,
    val currentCat: Cat? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = true
)