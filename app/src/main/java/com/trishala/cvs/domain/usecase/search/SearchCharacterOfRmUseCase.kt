package com.trishala.cvs.domain.usecase.search

import com.trishala.cvs.domain.model.CharacterRmModel
import com.trishala.cvs.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchCharacterOfRmUseCase @Inject constructor(private val repository: CharacterRepository): ISearchCharacterOfRmUseCase {


    override  operator fun invoke(name: String, status: String?, species: String?, type: String?): Flow<List<CharacterRmModel>> {
        if (name.isBlank()) return flowOf(emptyList())

        return repository.searchCharacters(name, status, species, type)
    }

}