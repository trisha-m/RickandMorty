package com.trishala.cvs.domain.model

data class CharacterRmModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val species: String,
    val status: String,
    val origin: String,
    val type: String? ,
    val createdAt: String
)
