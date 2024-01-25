package com.aurelioklv.catalog.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.aurelioklv.catalog.ui.home.HomeScreenEvent

@Composable
fun BottomBarLayout(
    navController: NavHostController,
    onEvent: (HomeScreenEvent) -> Unit,
    navBackStackEntry: NavBackStackEntry?,
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CatalogTopAppBar(
                onEvent = onEvent,
                navBackStackEntry = navBackStackEntry
            )
        },
        bottomBar = {
            CatalogBottomNavigation(
                navController = navController,
                navBackStackEntry = navBackStackEntry
            )
        }
    ) {
        content(it)
    }
}

@Composable
fun NavigationRailLayout(
    navController: NavHostController,
    onEvent: (HomeScreenEvent) -> Unit,
    navBackStackEntry: NavBackStackEntry?,
    content: @Composable () -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        CatalogNavigationRail(
            navController = navController,
            navBackStackEntry = navBackStackEntry,
            onEvent = onEvent
        )
        content()
    }
}