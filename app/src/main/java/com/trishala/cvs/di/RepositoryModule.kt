package com.trishala.cvs.di

import com.trishala.cvs.data.repository.RmCharacterRepository
import com.trishala.cvs.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        impl: RmCharacterRepository
    ): CharacterRepository
}