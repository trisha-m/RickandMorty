package com.trishala.cvs.di

import com.trishala.cvs.domain.usecase.character.GetCharacterOfRm
import com.trishala.cvs.domain.usecase.character.IGetCharacterOfRmUseCase
import com.trishala.cvs.domain.usecase.search.ISearchCharacterOfRmUseCase
import com.trishala.cvs.domain.usecase.search.SearchCharacterOfRmUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    @Singleton
    abstract fun bindSearchCharacterUseCase(impl: SearchCharacterOfRmUseCase): ISearchCharacterOfRmUseCase

    @Binds
    @Singleton
    abstract fun bindGetCharacterDetailsUseCase(impl: GetCharacterOfRm): IGetCharacterOfRmUseCase
}