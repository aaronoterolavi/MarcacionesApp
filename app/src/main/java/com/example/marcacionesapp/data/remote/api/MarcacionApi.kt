package com.example.marcacionesapp.data.remote.api

import com.example.marcacionesapp.data.remote.dto.MarcacionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MarcacionApi {

    @POST("api/Marcacion/registrar")
    suspend fun enviarMarcacion(
        @Body marcacion: MarcacionDto
    ): Response<Unit>
}