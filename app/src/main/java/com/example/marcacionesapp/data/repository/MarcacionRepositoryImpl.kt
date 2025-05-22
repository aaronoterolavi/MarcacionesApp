package com.example.marcacionesapp.data.repository


import com.example.marcacionesapp.data.local.dao.MarcacionDao
import com.example.marcacionesapp.data.domain.model.Marcacion
import com.example.marcacionesapp.data.domain.repository.MarcacionRepository
import com.example.marcacionesapp.data.mapper.toDomain
import com.example.marcacionesapp.data.mapper.toDto
import com.example.marcacionesapp.data.mapper.toEntity
import com.example.marcacionesapp.data.remote.api.MarcacionApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarcacionRepositoryImpl @Inject constructor(
    private val dao: MarcacionDao,
    private val api: MarcacionApi
) : MarcacionRepository {

    override suspend fun registrarMarcacion(marcacion: Marcacion) {
        dao.insertMarcacion(marcacion.toEntity())
    }

    override fun obtenerMarcaciones(): Flow<List<Marcacion>> {
        return dao.getAllMarcaciones().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun obtenerContador(): Int {
        return dao.obtenerContador() ?: 0
    }

    override fun obtenerMarcacionesPorEstado(estado: Int): Flow<List<Marcacion>> {
        return dao.getMarcacionesPorEstado(estado).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun actualizarEstadoMarcacion(id: Int, estado: Int) {
        dao.actualizarEstadoMarcacion(id, estado)
    }

    override suspend fun sincronizarMarcacion(marcacion: Marcacion) {
        val dto = marcacion.toDto()
        val response = api.enviarMarcacion(dto)
        if (response.isSuccessful) {
            actualizarEstadoMarcacion(marcacion.id, 2)
        } else {
            actualizarEstadoMarcacion(marcacion.id, 3)
        }
    }
}