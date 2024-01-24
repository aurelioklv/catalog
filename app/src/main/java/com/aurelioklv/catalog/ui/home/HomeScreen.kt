package com.aurelioklv.catalog.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aurelioklv.catalog.R
import com.aurelioklv.catalog.data.model.Cat
import com.aurelioklv.catalog.ui.common.ErrorScreen
import com.aurelioklv.catalog.ui.theme.CatalogTheme

@Composable
fun HomeScreen(
    state: HomeScreenState,
    retryAction: (HomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when {
        state.isLoading -> LoadingScreen()
        state.isError -> ErrorScreen(retryAction = { retryAction(HomeScreenEvent.RefreshImage) })

        else -> {
            CatPhotoList(
                cats = state.cats,
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
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large))
    ) {
        items(items = cats, key = { it.id }) {
            CatCard(cat = it)
        }
    }
}

@Composable
fun CatCard(cat: Cat, modifier: Modifier = Modifier) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
    ) {
        val breed = cat.breeds
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
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
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val annotatedCatId = AnnotatedString(cat.id)
                    ClickableText(
                        text = annotatedCatId,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                        style = MaterialTheme.typography.titleMedium,
                        onClick = { clipboardManager.setText(annotatedCatId) }
                    )
                    Text(
                        text = if (!breed.isNullOrEmpty()) breed.first().name else "",
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_small))
                ) {
                    Text(
                        text = "Width: ${cat.imageWidth}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Height: ${cat.imageHeight}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
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
    val mockData = Cat(
        id = "qaZwsX23",
        imageUrl = "",
        imageWidth = 800,
        imageHeight = 600,
        breeds = emptyList()
    )
    CatCard(
        cat = mockData,
        modifier = Modifier.fillMaxWidth()
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