package com.trishala.cvs.presentation.features.search.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.trishala.cvs.R
import com.trishala.cvs.presentation.features.search.mvi.SearchIntent
import com.trishala.cvs.presentation.features.search.mvi.SearchState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchState,
    onIntent: (SearchIntent) -> Unit,
    onCharacterClick: (Int) -> Unit
) {
    val isEmpty =
        state.query.isNotBlank() &&
                !state.isLoading &&
                state.error == null &&
                state.items.isEmpty()

    val paddingScreen = dimensionResource(id = R.dimen.padding_screen)
    val spaceMd = dimensionResource(id = R.dimen.space_md)
    val spaceXl = dimensionResource(id = R.dimen.space_xl)
    val spaceLg = dimensionResource(id = R.dimen.space_lg)
    val spaceSm = dimensionResource(id = R.dimen.space_sm)

    val gridTop = dimensionResource(id = R.dimen.grid_top_padding)
    val gridBottom = dimensionResource(id = R.dimen.grid_bottom_padding)


    val isLandscape =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val fontScale = LocalDensity.current.fontScale

    val minCellSize = when {
        isLandscape && fontScale >= 1.4f ->
            dimensionResource(id = R.dimen.grid_cell_min_landscape_very_large_font)

        isLandscape && fontScale >= 1.2f ->
            dimensionResource(id = R.dimen.grid_cell_min_landscape_large_font)

        isLandscape ->
            dimensionResource(id = R.dimen.grid_cell_min_landscape)

        fontScale >= 1.2f ->
            dimensionResource(id = R.dimen.grid_cell_min_portrait_large_font)

        else ->
            dimensionResource(id = R.dimen.grid_cell_min_portrait)
    }

    val gridSpacing = if (fontScale >= 1.2f)
        dimensionResource(id = R.dimen.grid_spacing_large_font)
    else
        dimensionResource(id = R.dimen.grid_spacing_normal)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.title_search)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(paddingScreen)
        ) {

            OutlinedTextField(
                value = state.query,
                onValueChange = { onIntent(SearchIntent.QueryChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text(stringResource(id = R.string.label_search_characters)) }
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spaceMd)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(spaceSm)
            ) {
                FilterChip(
                    selected = state.status == "alive",
                    onClick = {
                        onIntent(
                            SearchIntent.StatusChanged(
                                if (state.status == "alive") null else "alive"
                            )
                        )
                    },
                    label = { Text(stringResource(id = R.string.chip_alive)) }
                )

                FilterChip(
                    selected = state.status == "dead",
                    onClick = {
                        onIntent(
                            SearchIntent.StatusChanged(
                                if (state.status == "dead") null else "dead"
                            )
                        )
                    },
                    label = { Text(stringResource(id = R.string.chip_dead)) }
                )

                FilterChip(
                    selected = state.status == "unknown",
                    onClick = {
                        onIntent(
                            SearchIntent.StatusChanged(
                                if (state.status == "unknown") null else "unknown"
                            )
                        )
                    },
                    label = { Text(stringResource(id = R.string.chip_unknown)) }
                )
            }

            if (state.isLoading) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = spaceXl),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            if (state.error != null) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = spaceLg)
                )
            }

            if (isEmpty) {
                Text(
                    text = stringResource(id = R.string.text_no_characters_found),
                    modifier = Modifier.padding(top = spaceLg)
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = minCellSize),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = gridTop),
                verticalArrangement = Arrangement.spacedBy(gridSpacing),
                horizontalArrangement = Arrangement.spacedBy(gridSpacing),
                contentPadding = PaddingValues(bottom = gridBottom)
            ) {
                items(state.items, key = { it.id }) { item ->
                    CharacterGridItem(
                        name = item.name,
                        imageUrl = item.imageUrl,
                        onClick = { onCharacterClick(item.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterGridItem(
    name: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    val corner = dimensionResource(id = R.dimen.card_corner_radius)
    val textPad = dimensionResource(id = R.dimen.card_text_padding)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(corner),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(corner)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(textPad),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
