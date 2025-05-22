package com.example.marcacionesapp.data.remote.dto

data class UsuarioDto(
    val iCodUsuario: Int,
    val vUsuario: String,
    val vPassword: String,
    val vNombres: String,
    val vApellidoPaterno: String,
    val vApellidoMaterno: String,
    val vDni: String,
    val vSede: String,
    val dtFechaIngreso: String,
    val vCorreoElectronico: String,
    val vTelefono: String
)