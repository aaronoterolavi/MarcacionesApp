package com.example.marcacionesapp.data.remote.dto

data class MarcacionDto (

    val id: Int = 0,
    val iCodUsuario: Int?,
    val iCodTipoMarcacion: Int,
    val dtFechaMarcacion: String,
    val vLatitud: String,
    val vLongitud: String,
    val estado: Int,
    val vfoto: String? =null,
    val contador: Int
)