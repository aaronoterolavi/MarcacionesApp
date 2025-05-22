package com.example.marcacionesapp.di

import com.example.marcacionesapp.data.domain.repository.MarcacionRepository
import com.example.marcacionesapp.data.domain.repository.UsuarioRepository
import com.example.marcacionesapp.data.repository.MarcacionRepositoryImpl
import com.example.marcacionesapp.data.repository.UsuarioRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindMarcacionRepository(
        impl: MarcacionRepositoryImpl
    ): MarcacionRepository


    @Binds
    @Singleton
    abstract fun bindUsuarioRepository(
        impl: UsuarioRepositoryImpl
    ): UsuarioRepository



}
