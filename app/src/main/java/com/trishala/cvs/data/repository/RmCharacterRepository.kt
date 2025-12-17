package com.trishala.cvs.data.repository

import android.util.Log.d
import com.trishala.cvs.data.remote.api.RickAndMortyApi
import com.trishala.cvs.data.remote.mapper.toDomain
import com.trishala.cvs.domain.model.CharacterRmModel
import com.trishala.cvs.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RmCharacterRepository @Inject constructor(private val api: RickAndMortyApi): CharacterRepository {

    override fun getRmCharacterById(id: Int): Flow<CharacterRmModel> = flow{
        val res=api.getCharacterById(id)
        emit( res.toDomain())
    }

    override fun searchCharacters(name: String, status: String?, species: String?, type: String?
    ): Flow<List<CharacterRmModel>> = flow{
        val response = api.searchCharacters(
            name = name,
            status = status,
            species = species,
            type = type
        )

        val result = response.results.orEmpty().map {
            it.toDomain()
        }

        emit(result)
    }
        .flowOn(Dispatchers.IO)
    }
