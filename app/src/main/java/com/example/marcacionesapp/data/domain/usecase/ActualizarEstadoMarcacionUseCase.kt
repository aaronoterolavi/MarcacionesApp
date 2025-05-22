package com.example.marcacionesapp.data.domain.usecase

import com.example.marcacionesapp.data.domain.repository.MarcacionRepository
import javax.inject.Inject

class ActualizarEstadoMarcacionUseCase @Inject constructor(private val repository: MarcacionRepository) {
    suspend operator fun invoke(idMarcacion: Int, nuevoEstado: Int) {
        repository.actualizarEstadoMarcacion(idMarcacion, nuevoEstado)
    }
}