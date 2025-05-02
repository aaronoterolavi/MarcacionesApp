package com.example.marcacionesapp.data.repository

import com.example.marcacionesapp.data.dao.MarcacionDao
import com.example.marcacionesapp.data.dao.UsuarioDao
import com.example.marcacionesapp.data.entity.MarcacionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarcacionRepository @Inject constructor(private val dao: MarcacionDao) {

     suspend fun registrarMarcacion(marcacion: MarcacionEntity) {
        dao.insertMarcacion(marcacion)
    }

     fun obtenerMarcaciones(): Flow<List<MarcacionEntity>> {
        return dao.getAllMarcaciones().map { list -> list.map { it } }
    }

     suspend fun obtenerContador(): Int {
        return dao.obtenerContador() ?: 0
    }
}