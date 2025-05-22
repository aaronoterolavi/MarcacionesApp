package com.example.marcacionesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "T_Usuario")
data class UsuarioEntity (
    @PrimaryKey val iCodUsuario: Int,
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


){

}