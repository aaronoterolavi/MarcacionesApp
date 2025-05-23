package com.example.marcacionesapp.data.domain.usecase

import com.example.marcacionesapp.data.domain.repository.MarcacionRepository
import javax.inject.Inject

class ObtenerMarcacionesUseCase @Inject constructor(private val repository: MarcacionRepository) {
    operator fun invoke(iCodUsuario: Int) = repository.obtenerMarcaciones(iCodUsuario)
}