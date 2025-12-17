package com.trishala.cvs.presentation.features.search.mvi

data class SearchParams(
    val name: String,
    val status: String?,
    val species: String?,
    val type: String?
)
