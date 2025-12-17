package com.trishala.cvs.presentation.features.search.mvi

sealed interface SearchIntent {
    data class QueryChanged(val value: String) : SearchIntent
    data class StatusChanged(val value: String?) : SearchIntent
    data class SpeciesChanged(val value: String?) : SearchIntent
    data class TypeChanged(val value: String?) : SearchIntent
    data object ClearError : SearchIntent
}