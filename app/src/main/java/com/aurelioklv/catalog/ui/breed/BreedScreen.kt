package com.aurelioklv.catalog.ui.breed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.aurelioklv.catalog.R
import com.aurelioklv.catalog.data.model.Breed
import com.aurelioklv.catalog.ui.common.ErrorScreen
import com.aurelioklv.catalog.ui.home.LoadingScreen

@Composable
fun BreedScreen(
    state: BreedScreenState,
    retryAction: (BreedScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when {
        state.isLoading -> LoadingScreen()
        state.isError -> ErrorScreen(retryAction = { retryAction(BreedScreenEvent.GetBreeds) })
        else -> {
            BreedList(
                breeds = state.breeds,
                contentPadding = contentPadding,
                modifier = modifier.padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_medium)
                )
            )
        }
    }
}

@Composable
fun BreedList(
    breeds: List<Breed>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        items(items = breeds, key = { it.id }) {
            BreedCard(breed = it)
        }
    }
}

@Composable
fun BreedCard(breed: Breed, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = breed.name,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = breed.id,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}