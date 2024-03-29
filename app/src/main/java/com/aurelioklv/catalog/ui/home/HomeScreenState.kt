package com.aurelioklv.catalog.ui.home

import com.aurelioklv.catalog.data.model.Cat

data class HomeScreenState(
    val fetchAmount: Int = 0,
    val gridColumn: Int = 2,
    val hasBreeds: Int = 1,
    val cats: List<Cat> = emptyList(),
    val isSettingModalVisible: Boolean = false,
    val isShowingDetails: Boolean = false,
    val currentCat: Cat? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = true
)