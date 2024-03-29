package com.aurelioklv.catalog.domain.repository

import com.aurelioklv.catalog.data.model.DarkThemeConfig
import com.aurelioklv.catalog.data.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userPreferences: Flow<UserPreferences>

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)
    suspend fun toggleFetchAmount()
    suspend fun toggleGridColumn()
}