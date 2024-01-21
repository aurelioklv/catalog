package com.aurelioklv.catalog.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.aurelioklv.catalog.R
import com.aurelioklv.catalog.ui.home.HomeScreenEvent
import com.aurelioklv.catalog.ui.navigation.bottomNavigationItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogTopAppBar(
    onEvent: (HomeScreenEvent) -> Unit,
    navBackStackEntry: NavBackStackEntry?
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
            if (navBackStackEntry?.destination?.route == "home") {
                IconButton(onClick = { onEvent(HomeScreenEvent.RefreshImage) }) {
                    Icon(Icons.Filled.Refresh, contentDescription = null)
                }
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

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.offline),
            contentDescription = stringResource(R.string.error),
            modifier = Modifier.size(80.dp),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.error)
        )
        Text(
            text = stringResource(R.string.you_are_offline), modifier = Modifier.padding(
                dimensionResource(R.dimen.padding_small)
            )
        )
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Text(
                text = stringResource(R.string.retry),
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}

@Composable
fun EmptyScreen(title: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title)
    }
}