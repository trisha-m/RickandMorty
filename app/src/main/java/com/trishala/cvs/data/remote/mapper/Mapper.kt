package com.trishala.cvs.data.remote.mapper

import android.util.Log.i
import com.trishala.cvs.data.remote.dto.CharacterDto
import com.trishala.cvs.domain.model.CharacterRmModel

fun CharacterDto.toDomain(): CharacterRmModel{
    return CharacterRmModel(
        id=id,
        name=name,
        imageUrl = image,
        species=species,
        status= status,
        origin = origin?.name?.takeIf { it.isNotBlank() } ?: "Unknown",
        type = type.takeIf{ it.isNotBlank()},
        createdAt = created
    )
}