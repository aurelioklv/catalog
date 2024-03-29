package com.aurelioklv.catalog.ui.breed

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aurelioklv.catalog.R
import com.aurelioklv.catalog.data.fake.FakeDataSource.breeds
import com.aurelioklv.catalog.data.fake.FakeDataSource.cat
import com.aurelioklv.catalog.data.model.Breed
import com.aurelioklv.catalog.data.model.Cat
import com.aurelioklv.catalog.ui.common.ErrorScreen
import com.aurelioklv.catalog.ui.common.RatingBullet
import com.aurelioklv.catalog.ui.common.getColorFromHashCode
import com.aurelioklv.catalog.ui.home.LoadingScreen
import com.aurelioklv.catalog.ui.navigation.Screen
import com.aurelioklv.catalog.ui.theme.CatalogTheme

@Composable
fun BreedScreenRoute(
    viewModel: BreedViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isExpandedWidthSize: Boolean = false,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    when {
        state.isLoading -> LoadingScreen()
        state.isError -> ErrorScreen(retryAction = { onEvent(BreedScreenEvent.GetBreeds) })

        else -> {
            Log.i("EXPANDED", "isExpanded $isExpandedWidthSize")
            if (isExpandedWidthSize) {
                BreedListDetails(
                    state = state,
                    onItemClicked = { onEvent(BreedScreenEvent.GetBreed(it)) }
                )
            } else {
                BreedList(
                    state = state,
                    onItemClicked = {
                        onEvent(BreedScreenEvent.GetBreed(it))
                        navController.navigate(Screen.BreedDetails.route)
                    },
                    modifier = modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun BreedList(
    state: BreedScreenState,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val breeds = state.breeds
    BreedList(breeds = breeds, onItemClicked = onItemClicked, modifier = modifier)
}

@Composable
fun BreedList(
    breeds: List<Breed>,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = breeds, key = { it.id }) {
            BreedCard(breed = it, onItemClicked = onItemClicked)
        }
    }
}

@Composable
fun BreedCard(
    breed: Breed,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 2.dp, color = getColorFromHashCode(breed.hashCode())),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.clickable {
            onItemClicked(breed.id)
        }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = breed.name,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = breed.id,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}

@Composable
fun BreedDetailsRoute(
    viewModel: BreedViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val breed = state.currentBreed
    val catRef = state.currentBreedCatRef

    BreedDetails(breed = breed, catRef = catRef, modifier = modifier)
}

@Composable
fun BreedDetails(
    breed: Breed?,
    catRef: Cat?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(state = rememberScrollState()),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(catRef?.imageUrl)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            placeholder = painterResource(R.drawable.walking_cat),
            error = painterResource(R.drawable.sitting_cat)
        )
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            breed?.let {
                it.apply {
                    StringBreedData(title = "Breed", value = name)
                    StringBreedData(title = "Description", value = description)

                    StringBreedData(title = "Temperament", value = temperament)
                    StringBreedData(title = "Origin", value = origin)
                    StringBreedData(title = "Life Span", value = lifeSpan)
                    StringBreedData(title = "Alternative Names", value = altNames)

                    LeveledBreedData(title = "Child Friendly", value = childFriendly, max = 5)
                    LeveledBreedData(title = "Cat Friendly", value = catFriendly, max = 5)
                    LeveledBreedData(title = "Dog Friendly", value = dogFriendly, max = 5)
                    LeveledBreedData(title = "Stranger Friendly", value = strangerFriendly, max = 5)

                    LeveledBreedData(title = "Adaptability", value = adaptability, max = 5)
                    LeveledBreedData(title = "Affection Level", value = affectionLevel, max = 5)
                    LeveledBreedData(title = "Energy Level", value = energyLevel, max = 5)
                    LeveledBreedData(title = "Grooming", value = grooming, max = 5)
                    LeveledBreedData(title = "Health Issues", value = healthIssues, max = 5)
                    LeveledBreedData(title = "Intelligence", value = intelligence, max = 5)
                    LeveledBreedData(title = "Shedding Level", value = sheddingLevel, max = 5)
                    LeveledBreedData(title = "Social Needs", value = socialNeeds, max = 5)
                    LeveledBreedData(title = "Vocalisation", value = vocalisation, max = 5)
                    LeveledBreedData(title = "Bidability", value = bidability, max = 5)

                    BinaryBreedData(title = "Indoor", value = indoor)
                    BinaryBreedData(title = "Lap", value = lap)
                    BinaryBreedData(title = "Experimental", value = experimental)
                    BinaryBreedData(title = "Hairless", value = hairless)
                    BinaryBreedData(title = "Natural", value = natural)
                    BinaryBreedData(title = "Rare", value = rare)
                    BinaryBreedData(title = "Rex", value = rex)
                    BinaryBreedData(title = "Suppressed Tail", value = suppressedTail)
                    BinaryBreedData(title = "Short Legs", value = shortLegs)
                    BinaryBreedData(title = "Hypoallergenic", value = hypoallergenic)

                    StringBreedData(title = "CFA URL", value = cfaUrl, isLink = true)
                    StringBreedData(title = "Vetstreet URL", value = vetstreetUrl, isLink = true)
                    StringBreedData(
                        title = "VCA Hospitals URL",
                        value = vcahospitalsUrl,
                        isLink = true
                    )
                    StringBreedData(title = "Wikipedia URL", value = wikipediaUrl, isLink = true)
                }
            } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No breed")
            }
        }
    }
}

@Composable
fun BreedListDetails(
    state: BreedScreenState,
    onItemClicked: (String) -> Unit,
) {
    val breeds = state.breeds
    val breed = state.currentBreed
    val catRef = state.currentBreedCatRef

    BreedListDetails(
        breeds = breeds,
        breed = breed,
        catRef = catRef,
        onItemClicked = onItemClicked
    )
}

@Composable
fun BreedListDetails(
    breeds: List<Breed>,
    breed: Breed?,
    catRef: Cat?,
    onItemClicked: (String) -> Unit,
) {
    Row(modifier = Modifier.fillMaxSize()) {
        BreedList(
            breeds = breeds,
            onItemClicked = onItemClicked,
            modifier = Modifier.weight(1f)
        )
        BreedDetails(
            breed = breed,
            catRef = catRef,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun LeveledBreedData(title: String, value: Int?, max: Int) {
    Column {
        Text(
            text = "$title:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        if (value == null) {
            Text(text = "N/A", style = MaterialTheme.typography.bodyMedium)
        } else {
            RatingBullet(modifier = Modifier.padding(top = 8.dp), value = value, max = max)
        }
    }
}

@Composable
fun StringBreedData(title: String, value: String?, isLink: Boolean = false) {
    val localUriHandler = LocalUriHandler.current
    val text = if (value.isNullOrEmpty()) "N/A" else value
    val isValidLink = isLink && !value.isNullOrEmpty()
    Column {
        Text(
            text = "$title:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = if (isValidLink) FontStyle.Italic else FontStyle.Normal,
            textDecoration = if (isValidLink) TextDecoration.Underline else TextDecoration.None,
            modifier = Modifier.clickable(enabled = isLink && !value.isNullOrEmpty()) {
                if (isValidLink) {
                    localUriHandler.openUri(value!!)
                }
            }
        )
    }
}

@Composable
fun BinaryBreedData(title: String, value: Int?) {
    Column {
        Text(
            text = "$title:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = value?.let { if (it == 1) "Yes" else "No" } ?: "N/A",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun BreedListPreview() {
    CatalogTheme {
        BreedList(breeds = breeds, onItemClicked = {})
    }
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun BreedDetailsPreview() {
    CatalogTheme {
        BreedDetails(breed = breeds[0], catRef = null)
    }
}

@Preview(showBackground = true, apiLevel = 33, device = Devices.TABLET)
@Composable
fun BreedListDetailsPreview() {
    CatalogTheme {
        BreedListDetails(
            breeds = breeds,
            breed = breeds[0],
            catRef = cat,
            onItemClicked = {}
        )
    }
}