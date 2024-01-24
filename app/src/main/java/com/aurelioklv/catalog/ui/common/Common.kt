package com.aurelioklv.catalog.ui.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.aurelioklv.catalog.R
import com.aurelioklv.catalog.ui.home.HomeScreenEvent
import com.aurelioklv.catalog.ui.navigation.bottomNavItems

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
        bottomNavItems.forEach {
            val isSelected = navBackStackEntry?.destination?.route?.startsWith(it.route) ?: false
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = (if (isSelected) it.selectedIcon else it.unselectedIcon)!!,
                        contentDescription = stringResource(id = it.resourceId)
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

@Composable
fun RatingBullet(
    value: Int,
    max: Int,
    modifier: Modifier = Modifier,
    blankAlpha: Float = 0.25f,
    filledColor: Color = MaterialTheme.colorScheme.secondary,
    blankColor: Color = filledColor.copy(alpha = blankAlpha)
) {
    val count = maxOf(value, max)
    Canvas(
        modifier = modifier
            .width(200.dp)
            .height(20.dp)
    ) {
        val horizontalSize = size.width / count
        val verticalSize = size.height
        repeat(count) {
            val startX = horizontalSize * (it) + horizontalSize / 2
            val startY = verticalSize / 2
            drawCircle(
                color = if (it < value) filledColor else blankColor,
                radius = horizontalSize * 0.3f,
                center = Offset(x = startX, y = startY)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingBulletPreview() {
    RatingBullet(3, 5)
}