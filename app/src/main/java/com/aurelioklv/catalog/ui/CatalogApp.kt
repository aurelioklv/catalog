package com.aurelioklv.catalog.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aurelioklv.catalog.R
import com.aurelioklv.catalog.ui.home.HomeScreen
import com.aurelioklv.catalog.ui.home.HomeViewModel

@Composable
fun CatalogApp() {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)

    Scaffold(
        topBar = {
            CatalogTopAppBar(onClick = homeViewModel::getCats)
        }
    ) {
        HomeScreen(
            uiState = homeViewModel.uiState,
            retryAction = homeViewModel::getCats,
            modifier = Modifier.fillMaxSize(),
            contentPadding = it
        )
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