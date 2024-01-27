package com.aurelioklv.catalog.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val darkThemeConfig: DarkThemeConfig,
    val fetchAmount: Int,
    val gridColumn: Int,
) {
    companion object {
        val FETCH_AMOUNT_OPTIONS = listOf(10, 20, 50, 100)
        val GRID_COLUMN_OPTIONS = listOf(1, 2, 3, 4)

        fun getDefaultInstance(): UserPreferences {
            return UserPreferences(
                DarkThemeConfig.FOLLOW_SYSTEM,
                FETCH_AMOUNT_OPTIONS.first(),
                GRID_COLUMN_OPTIONS.first()
            )
        }
    }

}