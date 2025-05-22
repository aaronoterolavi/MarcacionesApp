package com.example.marcacionesapp.data.domain.usecase


import com.example.marcacionesapp.data.domain.model.Marcacion
import com.example.marcacionesapp.data.domain.repository.MarcacionRepository
import javax.inject.Inject

class SincronizarMarcacionUseCase @Inject constructor(
    private val repository: MarcacionRepository
) {
    suspend operator fun invoke(marcacion: Marcacion) {
        repository.sincronizarMarcacion(marcacion)
    }
}