package com.trishala.cvs.presentation.features.details.mvi

import com.trishala.cvs.domain.model.CharacterRmModel


sealed interface DetailsResult {
    data object Loading : DetailsResult
    data class Success(val data: CharacterRmModel) : DetailsResult
    data class Error(val message: String) : DetailsResult
}