package com.aurelioklv.catalog.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aurelioklv.catalog.R
import com.aurelioklv.catalog.data.model.Cat
import com.aurelioklv.catalog.ui.common.ErrorScreen
import com.aurelioklv.catalog.ui.common.getColorFromHashCode
import com.aurelioklv.catalog.ui.theme.CatalogTheme

@Composable
fun HomeScreen(
    state: HomeScreenState,
    retryAction: (HomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
    isExpandedWindowSize: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when {
        state.isLoading -> LoadingScreen()
        state.isError -> ErrorScreen(retryAction = { retryAction(HomeScreenEvent.RefreshImage) })

        else -> {
            CatPhotoList(
                cats = state.cats,
                isExpandedWindowSize = isExpandedWindowSize,
                contentPadding = contentPadding,
                modifier = modifier.padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                )
            )
        }
    }
}

@Composable
fun CatPhotoList(
    cats: List<Cat>,
    isExpandedWindowSize: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = if (isExpandedWindowSize) GridCells.Adaptive(200.dp) else GridCells.Fixed(3),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        items(items = cats, key = { it.id }) {
            CatCard(cat = it)
        }
    }
}

@Composable
fun CatCard(cat: Cat) {
    val breed = cat.breeds!!.first()
    Card(
        border = BorderStroke(width = 2.dp, color = getColorFromHashCode(breed.hashCode())),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        val breed = cat.breeds
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(cat.imageUrl)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.walking_cat),
            error = painterResource(R.drawable.offline)
        )
        Text(
            text = if (!breed.isNullOrEmpty()) breed.first().name else "",
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
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
    val mockData = Cat(
        id = "qaZwsX23",
        imageUrl = "",
        imageWidth = 800,
        imageHeight = 600,
        breeds = emptyList()
    )
    CatCard(
        cat = mockData,
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