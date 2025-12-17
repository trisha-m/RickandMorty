package com.trishala.cvs.presentation.features.details.mvi

sealed interface DetailsAction {
    data class Load(val id: Int) : DetailsAction
}