package com.trishala.cvs.domain.usecase.character

import com.trishala.cvs.domain.model.CharacterRmModel
import com.trishala.cvs.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetCharacterOfRm @Inject constructor(private val repository: CharacterRepository) : IGetCharacterOfRmUseCase{

    override fun invoke(id: Int): Flow<CharacterRmModel> {
        if (id<=0) return flowOf()

        return repository.getRmCharacterById(id)
    }
}