package com.aurelioklv.catalog.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aurelioklv.catalog.R
import com.aurelioklv.catalog.ui.breed.BreedScreen
import com.aurelioklv.catalog.ui.breed.BreedViewModel
import com.aurelioklv.catalog.ui.home.EmptyScreen
import com.aurelioklv.catalog.ui.home.HomeScreen
import com.aurelioklv.catalog.ui.home.HomeViewModel
import com.aurelioklv.catalog.ui.navigation.bottomNavigationItems

@Composable
fun CatalogApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val homeViewModel = hiltViewModel<HomeViewModel>()
    val breedViewModel = hiltViewModel<BreedViewModel>()

    Scaffold(
        topBar = {
            CatalogTopAppBar(onClick = homeViewModel::getCats)
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
                    uiState = homeViewModel.uiState,
                    retryAction = homeViewModel::getCats,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = padding
                )
            }
            composable(route = "breed") {
                BreedScreen(
                    uiState = breedViewModel.uiState,
                    retryAction = breedViewModel::getBreeds,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogTopAppBar(
    onClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            IconButton(onClick = onClick) {
                Icon(Icons.Filled.Refresh, contentDescription = null)
            }
        }
    )
}

@Composable
fun CatalogBottomNavigation(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry?
) {
    NavigationBar {
        bottomNavigationItems.forEach {
            val isSelected = it.title.lowercase() == navBackStackEntry?.destination?.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(it.title.lowercase()) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) it.selectedIcon else it.unselectedIcon,
                        contentDescription = it.title
                    )
                }
            )
        }
    }
}