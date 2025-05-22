package com.example.marcacionesapp.data.domain.usecase

import com.example.marcacionesapp.data.domain.model.Marcacion
import com.example.marcacionesapp.data.domain.repository.MarcacionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerMarcacionesPorEstadoUseCase @Inject constructor(private val repository: MarcacionRepository) {
    operator fun invoke(estado: Int): Flow<List<Marcacion>> = repository.obtenerMarcacionesPorEstado(estado)
}