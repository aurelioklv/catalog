package com.aurelioklv.catalog.ui.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.aurelioklv.catalog.data.model.DarkThemeConfig


@Composable
fun shouldUseDarkTheme(state: MainActivityUiState): Boolean =
    when (state) {
        MainActivityUiState.Loading -> isSystemInDarkTheme()
        is MainActivityUiState.Success -> when (state.userData.userPreferences.darkThemeConfig) {
            DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            DarkThemeConfig.LIGHT -> false
            DarkThemeConfig.DARK -> true
        }
    }
