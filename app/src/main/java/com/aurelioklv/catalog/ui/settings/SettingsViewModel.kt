package com.aurelioklv.catalog.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aurelioklv.catalog.data.model.DarkThemeConfig
import com.aurelioklv.catalog.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    val state: StateFlow<SettingsUiState> =
        userPreferencesRepository.userPreferences
            .map {
                SettingsUiState.Success(
                    settings = EditableSettings(
                        darkThemeConfig = it.darkThemeConfig,
                        fetchAmount = it.fetchAmount,
                        gridColumn = it.gridColumn
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = SettingsUiState.Loading
            )

    fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            userPreferencesRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    fun toggleFetchAmount() {
        viewModelScope.launch {
            userPreferencesRepository.toggleFetchAmount()
        }
    }

    fun toggleGridColumn() {
        viewModelScope.launch {
            userPreferencesRepository.toggleGridColumn()
        }
    }
}