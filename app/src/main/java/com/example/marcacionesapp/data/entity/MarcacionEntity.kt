package com.example.marcacionesapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.StateFlow

@Entity
data class MarcacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioId: Int?,
    val tipo: String,
    val timestamp: Long,
    val latitud: Double,
    val longitud: Double,
    val estado: MarcacionEstado,
    val foto: String? =null,
    val contador: Int
)