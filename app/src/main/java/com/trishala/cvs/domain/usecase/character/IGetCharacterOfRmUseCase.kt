package com.trishala.cvs.domain.usecase.character

import com.trishala.cvs.domain.model.CharacterRmModel
import kotlinx.coroutines.flow.Flow

interface IGetCharacterOfRmUseCase {
   operator fun invoke(id: Int): Flow<CharacterRmModel>
}