package com.aurelioklv.catalog.ui.settings

import com.aurelioklv.catalog.data.model.DarkThemeConfig

data class EditableSettings(
    val darkThemeConfig: DarkThemeConfig,
    val fetchAmount: Int,
    val gridColumn: Int,
)
