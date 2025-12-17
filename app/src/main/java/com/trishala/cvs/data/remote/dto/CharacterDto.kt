package com.trishala.cvs.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("origin") val origin: OriginDto,
    @SerializedName("image") val image: String,
    @SerializedName("created") val created: String
)

