package com.trishala.cvs.presentation.features.details.mvi

import com.trishala.cvs.domain.model.CharacterRmModel

data class DetailsState(
    val isLoading: Boolean = false,
    val data: CharacterRmModel? = null,
    val error: String? = null
)
