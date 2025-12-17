package com.trishala.cvs.presentation.features.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trishala.cvs.presentation.features.details.mvi.DetailsIntent
import com.trishala.cvs.presentation.features.details.ui.DetailsScreen
import com.trishala.cvs.presentation.features.details.viewmodel.DetailsViewModel

@Composable
fun DetailsRoute(
    id: Int,
    onBack: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.onIntent(DetailsIntent.ScreenOpened(id))
    }

    DetailsScreen(
        state = state,
        onIntent = viewModel::onIntent,
        onBack = onBack
    )
}