package com.aurelioklv.catalog.ui.settings

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(val settings: EditableSettings) : SettingsUiState
}