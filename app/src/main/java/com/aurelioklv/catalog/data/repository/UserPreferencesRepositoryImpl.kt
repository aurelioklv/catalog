package com.aurelioklv.catalog.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.aurelioklv.catalog.data.model.DarkThemeConfig
import com.aurelioklv.catalog.data.model.UserPreferences
import com.aurelioklv.catalog.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) : UserPreferencesRepository {
    override val userPreferences: Flow<UserPreferences> = dataStore.data

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        dataStore.updateData {
            it.copy(darkThemeConfig = darkThemeConfig)
        }
    }

    override suspend fun toggleFetchAmount() {
        dataStore.updateData { preferences ->
            val options = fetchAmountOptions
            val current = preferences.fetchAmount
            val next = options[(options.indexOf(current) + 1) % options.size]
            Log.v(TAG, "toggleFetchAmount $current -> $next")
            preferences.copy(fetchAmount = next)
        }
    }

    override suspend fun toggleGridColumn() {
        dataStore.updateData { preferences ->
            val options = gridColumnOptions
            val current = preferences.gridColumn
            val next = options[(options.indexOf(current) + 1) % options.size]
            Log.v(TAG, "toggleGridColumn $current -> $next")
            preferences.copy(gridColumn = next)
        }
    }

    companion object {
        const val TAG = "UserPreferencesRepositoryImpl"

        val fetchAmountOptions = UserPreferences.FETCH_AMOUNT_OPTIONS
        val gridColumnOptions = UserPreferences.GRID_COLUMN_OPTIONS
    }
}