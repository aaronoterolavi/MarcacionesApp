package com.example.marcacionesapp.data.domain.usecase

import com.example.marcacionesapp.data.domain.model.Usuario
import com.example.marcacionesapp.data.domain.repository.UsuarioRepository
import javax.inject.Inject

class ObtenerUsuarioUseCase @Inject constructor(private val repository: UsuarioRepository) {
    suspend fun execute(): Usuario? = repository.obtenerUsuario()
}