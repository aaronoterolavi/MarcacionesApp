package com.example.marcacionesapp.data.repository

import com.example.marcacionesapp.data.dao.UsuarioDao
import com.example.marcacionesapp.data.entity.UsuarioEntity
import javax.inject.Inject

class LoginRepository @Inject constructor(private val dao: UsuarioDao) {


    suspend fun login(usuario: String, password : String): UsuarioEntity? {
       return  dao.login(usuario,password )
    }
}