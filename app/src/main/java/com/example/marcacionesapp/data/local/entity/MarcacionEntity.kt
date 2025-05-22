package com.example.marcacionesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "T_marcaciones")
data class MarcacionEntity(
    @PrimaryKey(autoGenerate = true)
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