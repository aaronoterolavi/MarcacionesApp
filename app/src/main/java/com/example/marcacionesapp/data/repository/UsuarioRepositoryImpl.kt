package com.example.marcacionesapp.data.repository

import android.util.Log
import com.example.marcacionesapp.data.domain.model.Usuario
import com.example.marcacionesapp.data.domain.repository.UsuarioRepository
import com.example.marcacionesapp.data.local.dao.UsuarioDao
import com.example.marcacionesapp.data.mapper.toDomain
import com.example.marcacionesapp.data.mapper.toEntity
import com.example.marcacionesapp.data.remote.api.UsuarioApi
import com.example.marcacionesapp.data.remote.dto.LoginRequestDto
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val api : UsuarioApi,
    private val dao : UsuarioDao
): UsuarioRepository{

    override suspend fun login(usuario:String , password:String):Usuario{
        val usuarioDto=api.login(LoginRequestDto(usuario,password))
        Log.e("usuarioDto", usuarioDto.toString())
        val domainUser = usuarioDto.toDomain()
        dao.insertarUsuario(domainUser.toEntity())
        return domainUser
    }

    override suspend fun obtenerUsuario(): Usuario?=dao.obtenerUsuario()?.toDomain()

    override suspend fun guardarUsuario(usuario: Usuario) =dao.insertarUsuario(usuario.toEntity())
}