package com.trishala.cvs.presentation.features.search.mvi

sealed interface SearchAction {
    data class ExecuteSearch(val params: SearchParams) : SearchAction
    data object ClearResults : SearchAction
}