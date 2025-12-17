package com.trishala.cvs.presentation.features.details.ui

import android.content.res.Configuration
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trishala.cvs.R
import com.trishala.cvs.presentation.features.details.mvi.DetailsIntent
import com.trishala.cvs.presentation.features.details.mvi.DetailsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    state: DetailsState,
    onIntent: (DetailsIntent) -> Unit,
    onBack: () -> Unit
) {
    val paddingScreen = dimensionResource(id = R.dimen.padding_details_screen)
    val spaceLg = dimensionResource(id = R.dimen.space_lg)
    val corner = dimensionResource(id = R.dimen.card_corner_radius)
    val cardPad = dimensionResource(id = R.dimen.card_content_padding)
    val spacer = dimensionResource(id = R.dimen.space_md)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.title_character_details)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(id = R.string.cd_back)
                        )
                    }
                }
            )
        }
    ) { padding ->

        when {
            state.isLoading -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) { CircularProgressIndicator() }

            state.error != null -> Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(paddingScreen),
                verticalArrangement = Arrangement.spacedBy(spaceLg)
            ) {
                Text(state.error, color = MaterialTheme.colorScheme.error)
                Button(onClick = { onIntent(DetailsIntent.Retry) }) {
                    Text(stringResource(id = R.string.retry))
                }
            }

            state.data != null -> {
                val c = state.data
                val context = LocalContext.current
                val isLandscape =
                    LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(paddingScreen),
                    verticalArrangement = Arrangement.spacedBy(spaceLg)
                ) {
                    Card(
                        shape = RoundedCornerShape(corner),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isLandscape) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(cardPad),
                                horizontalArrangement = Arrangement.spacedBy(spaceLg)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(c.imageUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = c.name,
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(corner)),
                                    contentScale = ContentScale.Crop
                                )

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .verticalScroll(rememberScrollState()),
                                    verticalArrangement = Arrangement.spacedBy(spacer)
                                ) {
                                    Text(
                                        c.name,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color.Black
                                    )

                                    Text(
                                        stringResource(
                                            id = R.string.format_status_species,
                                            c.status,
                                            c.species
                                        ),
                                        color = Color.Black
                                    )

                                    Text(
                                        stringResource(id = R.string.format_origin, c.origin),
                                        color = Color.Black
                                    )

                                    c.type?.let {
                                        Text(
                                            stringResource(id = R.string.format_type, it),
                                            color = Color.Black
                                        )
                                    }

                                    Text(
                                        stringResource(id = R.string.format_created, c.createdAt),
                                        color = Color.Black
                                    )
                                }
                            }
                        } else {
                            Column(
                                Modifier
                                    .padding(cardPad)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(c.imageUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = c.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(corner)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(Modifier.height(spacer))

                                Text(
                                    c.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.Black
                                )

                                Text(
                                    stringResource(
                                        id = R.string.format_status_species,
                                        c.status,
                                        c.species
                                    ),
                                    color = Color.Black
                                )

                                Text(
                                    stringResource(id = R.string.format_origin, c.origin),
                                    color = Color.Black
                                )

                                c.type?.let {
                                    Text(
                                        stringResource(id = R.string.format_type, it),
                                        color = Color.Black
                                    )
                                }

                                Text(
                                    stringResource(id = R.string.format_created, c.createdAt),
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }

            else -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
        }
    }
}
