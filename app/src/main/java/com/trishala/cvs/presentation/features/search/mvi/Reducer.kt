package com.trishala.cvs.presentation.features.search.mvi

object Reducer {
     fun reduce(old: SearchState, result: SearchResult): SearchState =
        when (result) {
            SearchResult.Loading -> old.copy(isLoading = true, error = null)
            is SearchResult.Success -> old.copy(
                isLoading = false,
                items = result.data,
                error = null
            )

            is SearchResult.Error -> old.copy(isLoading = false, error = result.message)
            SearchResult.Cleared -> old.copy(isLoading = false, items = emptyList(), error = null)
        }
}