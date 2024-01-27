package com.aurelioklv.catalog.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.ui.graphics.vector.ImageVector
import com.aurelioklv.catalog.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null
) {
    data object Home : Screen("home", R.string.home, Icons.Filled.Home, Icons.Outlined.Home)
    data object Breeds :
        Screen("breeds", R.string.breeds, Icons.Filled.Pets, Icons.Outlined.Pets)

    data object BreedDetails :
        Screen("breed", R.string.breed_details)

    data object Categories :
        Screen("categories", R.string.categories, Icons.Filled.Category, Icons.Outlined.Category)
}

val menuItems = listOf(
    Screen.Home,
    Screen.Breeds,
    Screen.Categories
)