package com.example.marcacionesapp.data.domain.usecase

import com.example.marcacionesapp.data.domain.model.Usuario
import com.example.marcacionesapp.data.domain.repository.UsuarioRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: UsuarioRepository) {
    suspend fun execute(usuario: String, password: String): Usuario =
        repository.login(usuario, password)
}