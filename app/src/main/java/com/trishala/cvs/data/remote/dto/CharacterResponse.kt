package com.trishala.cvs.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("results") val results: List<CharacterDto>?
)

