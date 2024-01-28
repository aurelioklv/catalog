package com.aurelioklv.catalog.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aurelioklv.catalog.ui.common.CatalogBottomNavigation
import com.aurelioklv.catalog.ui.common.CatalogNavHost
import com.aurelioklv.catalog.ui.common.CatalogNavigationRail
import com.aurelioklv.catalog.ui.common.CatalogTopAppBar
import com.aurelioklv.catalog.ui.common.shouldShowBottomBar
import com.aurelioklv.catalog.ui.common.shouldShowNavRail
import com.aurelioklv.catalog.ui.common.shouldShowTopAppBar
import com.aurelioklv.catalog.ui.home.HomeViewModel

@Composable
fun CatalogApp(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val homeViewModel = hiltViewModel<HomeViewModel>()

    Scaffold(
        topBar = {
            if (shouldShowTopAppBar(windowSizeClass)) {
                CatalogTopAppBar(
                    onEvent = homeViewModel::onEvent,
                )
            }
        },
        bottomBar = {
            if (shouldShowBottomBar(windowSizeClass)) {
                CatalogBottomNavigation(
                    navController = navController,
                    navBackStackEntry = navBackStackEntry
                )
            }
        }
    ) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (shouldShowNavRail(windowSizeClass)) {
                CatalogNavigationRail(
                    navController = navController,
                    navBackStackEntry = navBackStackEntry,
                    onEvent = homeViewModel::onEvent
                )
            }
            CatalogNavHost(windowSizeClass = windowSizeClass, navController = navController)
        }
    }
}