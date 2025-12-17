package com.trishala.cvs.data.remote.api

import com.trishala.cvs.data.remote.dto.CharacterDto
import com.trishala.cvs.data.remote.dto.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("api/character")
    suspend fun searchCharacters(
        @Query("name") name: String,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null
    ): CharacterResponse



    @GET("api/character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterDto
}
