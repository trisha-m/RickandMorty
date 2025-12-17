package com.trishala.cvs.domain.usecase.search

import com.trishala.cvs.domain.model.CharacterRmModel
import kotlinx.coroutines.flow.Flow

interface ISearchCharacterOfRmUseCase {
  fun invoke(name: String, status: String? =null, species: String? =null, type: String?= null): Flow<List<CharacterRmModel>>
}