package com.trishala.cvs.presentation.features.details.mvi

sealed interface DetailsIntent {
    data class ScreenOpened(val id: Int) : DetailsIntent
    data object Retry : DetailsIntent
    data object ClearError : DetailsIntent
}