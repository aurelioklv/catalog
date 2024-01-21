package com.aurelioklv.catalog.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aurelioklv.catalog.ui.breed.BreedScreen
import com.aurelioklv.catalog.ui.breed.BreedViewModel
import com.aurelioklv.catalog.ui.common.CatalogBottomNavigation
import com.aurelioklv.catalog.ui.common.CatalogTopAppBar
import com.aurelioklv.catalog.ui.common.EmptyScreen
import com.aurelioklv.catalog.ui.home.HomeScreen
import com.aurelioklv.catalog.ui.home.HomeViewModel

@Composable
fun CatalogApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val homeViewModel = hiltViewModel<HomeViewModel>()
    val breedViewModel = hiltViewModel<BreedViewModel>()

    val homeScreenState by homeViewModel.state.collectAsState()
    val breedScreenState by breedViewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CatalogTopAppBar(
                onEvent = homeViewModel::onEvent,
                navBackStackEntry = navBackStackEntry
            )
        },
        bottomBar = {
            CatalogBottomNavigation(
                navController = navController,
                navBackStackEntry = navBackStackEntry
            )
        }
    ) { padding ->
        NavHost(navController = navController, startDestination = "home") {
            composable(route = "home") {
                HomeScreen(
                    state = homeScreenState,
                    retryAction = homeViewModel::onEvent,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = padding
                )
            }
            composable(route = "breed") {
                BreedScreen(
                    state = breedScreenState,
                    retryAction = breedViewModel::onEvent,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = padding
                )
            }
            composable(route = "settings") {
                EmptyScreen(title = "Settings Screen")
            }
        }
    }
}