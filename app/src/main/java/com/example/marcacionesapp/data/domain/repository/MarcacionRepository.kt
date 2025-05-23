package com.example.marcacionesapp.data.domain.repository

import com.example.marcacionesapp.data.domain.model.Marcacion
import kotlinx.coroutines.flow.Flow

interface MarcacionRepository {
    suspend fun registrarMarcacion(marcacion: Marcacion)
    suspend fun obtenerContador(): Int
    fun obtenerMarcaciones(iCodUsuario: Int): Flow<List<Marcacion>>
    fun obtenerMarcacionesPorEstado(iCodUsuario:Int,estado: Int): Flow<List<Marcacion>>
    suspend fun actualizarEstadoMarcacion(id: Int, estado: Int)
    suspend fun sincronizarMarcacion(marcacion: Marcacion)
}