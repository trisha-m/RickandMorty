package com.trishala.cvs.domain.repository

import com.trishala.cvs.domain.model.CharacterRmModel
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun searchCharacters(name: String, status: String? =null, species: String? =null, type: String? = null): Flow<List<CharacterRmModel>>

    fun getRmCharacterById(id: Int): Flow<CharacterRmModel>
}