package com.trishala.cvs.presentation.features.details.mvi



object DetailsReducer {
    fun reduce(old: DetailsState, result: DetailsResult): DetailsState =
        when (result) {
            DetailsResult.Loading -> old.copy(isLoading = true, error = null)
            is DetailsResult.Success -> old.copy(isLoading = false, data = result.data, error = null)
            is DetailsResult.Error -> old.copy(isLoading = false, error = result.message)
        }
}
