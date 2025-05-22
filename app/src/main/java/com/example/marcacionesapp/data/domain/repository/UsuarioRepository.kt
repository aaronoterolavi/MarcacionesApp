package com.example.marcacionesapp.data.domain.repository

import com.example.marcacionesapp.data.domain.model.Usuario

interface UsuarioRepository {
    suspend fun login(user: String, password: String): Usuario
    suspend fun obtenerUsuario(): Usuario?
    suspend fun guardarUsuario(user: Usuario)
}