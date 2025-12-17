package com.trishala.cvs.presentation.features.search.mvi

import com.trishala.cvs.domain.model.CharacterRmModel

sealed interface SearchResult {

        data object Loading : SearchResult
        data object Cleared : SearchResult
        data class Success(val data: List<CharacterRmModel>) : SearchResult
        data class Error(val message: String) : SearchResult
    }

