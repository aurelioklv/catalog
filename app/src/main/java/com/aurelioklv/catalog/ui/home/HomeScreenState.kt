package com.aurelioklv.catalog.ui.home

import com.aurelioklv.catalog.data.network.model.NetworkCat

data class HomeScreenState(
    val fetchAmount: Int = 0,
    val gridColumn: Int = 2,
    val hasBreeds: Int = 1,
    val networkCats: List<NetworkCat> = emptyList(),
    val isSettingModalVisible: Boolean = false,
    val isShowingDetails: Boolean = false,
    val currentNetworkCat: NetworkCat? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = true
)