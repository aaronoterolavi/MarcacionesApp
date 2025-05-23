package com.example.marcacionesapp.data.repository


import com.example.marcacionesapp.data.dataStore.UsuarioSessionManager
import com.example.marcacionesapp.data.domain.model.Usuario
import com.example.marcacionesapp.data.domain.repository.UsuarioRepository
import com.example.marcacionesapp.data.local.dao.UsuarioDao
import com.example.marcacionesapp.data.mapper.toDomain
import com.example.marcacionesapp.data.mapper.toEntity
import com.example.marcacionesapp.data.remote.api.UsuarioApi
import com.example.marcacionesapp.data.remote.dto.LoginRequestDto
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val api : UsuarioApi,
    private val dao : UsuarioDao,
    private val sessionManager: UsuarioSessionManager
): UsuarioRepository{

    override suspend fun login(usuario: String, password: String): Usuario {
        val usuarioDto = api.login(LoginRequestDto(usuario, password))
        if (usuarioDto.iCodUsuario == 0) {
            throw Exception("Usuario o contraseña inválidos")
        }
        val domainUser = usuarioDto.toDomain()
        dao.insertarUsuario(domainUser.toEntity())
        return domainUser
    }


override suspend fun obtenerUsuario(): Usuario? {
    val userId = sessionManager.obtenerUsuarioSesion().first() ?: return null
    val usuarioEntity = dao.obtenerUsuario(userId)
    return usuarioEntity?.toDomain()
}


    override suspend fun guardarUsuario(usuario: Usuario) =dao.insertarUsuario(usuario.toEntity())
}