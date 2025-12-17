package com.trishala.cvs.presentation.features.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trishala.cvs.presentation.features.search.ui.SearchScreen
import com.trishala.cvs.presentation.features.search.viewmodel.SearchViewModel

@Composable
fun SearchRoute(
    onCharacterClick: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(
        state = state,
        onIntent = viewModel::onIntent,
        onCharacterClick = onCharacterClick
    )
}