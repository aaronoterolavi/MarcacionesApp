package com.example.marcacionesapp.data.remote.api


import com.example.marcacionesapp.data.remote.dto.LoginRequestDto
import com.example.marcacionesapp.data.remote.dto.UsuarioDto
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApi {
    @POST("api/Usuario/Login")
    suspend fun login(
        @Body request: LoginRequestDto):UsuarioDto
}