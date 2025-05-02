package com.example.marcacionesapp.data.EntryPoint

import com.example.marcacionesapp.data.dao.UsuarioDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//borrar cuando tengas el API
@EntryPoint
@InstallIn(SingletonComponent::class)
interface UsuarioDaoEntryPoint {
    fun usuarioDao(): UsuarioDao
}