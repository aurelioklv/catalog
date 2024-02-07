package com.aurelioklv.catalog.ui.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.aurelioklv.catalog.data.local.preferences.DarkThemeConfig


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

fun shouldShowTopAppBar(windowSizeClass: WindowSizeClass): Boolean {
    return windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
}

fun shouldShowBottomBar(windowSizeClass: WindowSizeClass): Boolean {
    return windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
}

fun shouldShowNavRail(windowSizeClass: WindowSizeClass): Boolean {
    return !shouldShowBottomBar(windowSizeClass)
}

fun getColorFromHashCode(hashCode: Int): Color {
    val alpha = 1f
    val red = (hashCode and 0xFF0000 shr 16) / 255.0f
    val green = (hashCode and 0x00FF00 shr 8) / 255.0f
    val blue = (hashCode and 0x0000FF) / 255.0f
    return Color(red, green, blue, alpha)
}