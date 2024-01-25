package com.aurelioklv.catalog.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aurelioklv.catalog.ui.breed.BreedDetails
import com.aurelioklv.catalog.ui.breed.BreedScreen
import com.aurelioklv.catalog.ui.breed.BreedViewModel
import com.aurelioklv.catalog.ui.common.BottomBarLayout
import com.aurelioklv.catalog.ui.common.EmptyScreen
import com.aurelioklv.catalog.ui.common.NavigationRailLayout
import com.aurelioklv.catalog.ui.home.HomeScreen
import com.aurelioklv.catalog.ui.home.HomeViewModel
import com.aurelioklv.catalog.ui.navigation.Screen

@Composable
fun CatalogApp(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val homeViewModel = hiltViewModel<HomeViewModel>()
    val breedViewModel = hiltViewModel<BreedViewModel>()

    val homeScreenState by homeViewModel.state.collectAsState()
    val breedScreenState by breedViewModel.state.collectAsState()

    val navHost = remember {
        movableContentOf<PaddingValues> { innerPadding ->
            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(route = Screen.Home.route) {
                    HomeScreen(
                        state = homeScreenState,
                        retryAction = homeViewModel::onEvent,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = innerPadding
                    )
                }
                composable(route = Screen.Breeds.route) {
                    BreedScreen(
                        isExpandedWindowSize = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded,
                        state = breedScreenState,
                        onEvent = breedViewModel::onEvent,
                        navController = navController,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = innerPadding
                    )
                }
                composable(route = Screen.Settings.route) {
                    EmptyScreen(title = "Settings Screen")
                }
                composable(
                    route = Screen.BreedDetails.route,
                ) {
                    BreedDetails(
                        breed = breedScreenState.currentBreed,
                        catRef = breedScreenState.currentBreedCatRef,
                        contentPadding = innerPadding
                    )
                }
            }
        }
    }

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            BottomBarLayout(
                navController = navController,
                onEvent = homeViewModel::onEvent,
                navBackStackEntry = navBackStackEntry,
            ) {
                navHost(it)
            }
        }

        else -> {
            NavigationRailLayout(
                navController = navController,
                onEvent = homeViewModel::onEvent,
                navBackStackEntry = navBackStackEntry
            ) {
                navHost(PaddingValues())
            }
        }
    }
}