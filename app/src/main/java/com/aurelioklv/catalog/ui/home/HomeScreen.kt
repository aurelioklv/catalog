package com.aurelioklv.catalog.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aurelioklv.catalog.R
import com.aurelioklv.catalog.data.fake.FakeDataSource
import com.aurelioklv.catalog.data.model.Cat
import com.aurelioklv.catalog.ui.common.ErrorScreen
import com.aurelioklv.catalog.ui.common.getColorFromHashCode
import com.aurelioklv.catalog.ui.theme.CatalogTheme

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    isExpandedWidthSize: Boolean = false,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    when {
        state.isLoading -> LoadingScreen()
        state.isError -> ErrorScreen(retryAction = { onEvent(HomeScreenEvent.RefreshImage) })

        else -> {
            CatPhotoList(
                state = state,
                onClick = { onEvent(HomeScreenEvent.SaveCatImage(it)) },
                isExpandedWindowSize = isExpandedWidthSize,
                modifier = modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun CatPhotoList(
    state: HomeScreenState,
    onClick: (Cat) -> Unit,
    isExpandedWindowSize: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val cats = state.cats
    val gridColumn = state.gridColumn
    CatPhotoList(
        cats = cats,
        gridColumn = gridColumn,
        onClick = onClick,
        isExpandedWindowSize = isExpandedWindowSize,
        modifier = modifier,
        contentPadding = contentPadding
    )
}

@Composable
fun CatPhotoList(
    cats: List<Cat>,
    gridColumn: Int,
    onClick: (Cat) -> Unit,
    isExpandedWindowSize: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = if (isExpandedWindowSize) GridCells.Adaptive(200.dp) else GridCells.Fixed(
            gridColumn
        ),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        items(items = cats, key = { it.id }) {
            CatCard(cat = it, onClick = onClick)
        }
    }
}

@Composable
fun CatCard(cat: Cat, onClick: (Cat) -> Unit) {
    val breeds = cat.breeds
    Card(
        border = BorderStroke(
            width = 2.dp,
            color = if (!breeds.isNullOrEmpty()) getColorFromHashCode(breeds.hashCode()) else Color.Unspecified
        ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(1f),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(cat.imageUrl)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.walking_cat),
                error = painterResource(R.drawable.offline)
            )
            IconButton(onClick = { onClick(cat) }, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    imageVector = Icons.Outlined.FileDownload,
                    contentDescription = stringResource(R.string.download_image)
                )
            }
            Text(
                text = if (!breeds.isNullOrEmpty()) breeds.first().name else "N/A",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                    .align(Alignment.BottomCenter),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(R.string.loading))
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CatCardPreview() {
    CatCard(
        cat = FakeDataSource.cat,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    CatalogTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true, apiLevel = 32)
@Composable
fun ErrorScreenPreview() {
    CatalogTheme {
        ErrorScreen(retryAction = {})
    }
}