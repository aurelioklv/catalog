package com.aurelioklv.catalog.ui.home

import com.aurelioklv.catalog.data.model.Cat

sealed interface HomeUiState {
    data class Success(val cats: List<Cat>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}