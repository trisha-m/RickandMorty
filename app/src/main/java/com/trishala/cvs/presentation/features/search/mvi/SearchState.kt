package com.trishala.cvs.presentation.features.search.mvi

import com.trishala.cvs.domain.model.CharacterRmModel

data class SearchState(
    val query: String = "",
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val isLoading: Boolean = false,
    val items: List<CharacterRmModel> = emptyList(),
    val error: String? = null
)