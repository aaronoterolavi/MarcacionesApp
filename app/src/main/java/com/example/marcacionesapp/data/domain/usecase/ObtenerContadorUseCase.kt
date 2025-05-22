package com.example.marcacionesapp.data.domain.usecase

import com.example.marcacionesapp.data.domain.repository.MarcacionRepository
import javax.inject.Inject

class ObtenerContadorUseCase @Inject constructor(
    private val repository: MarcacionRepository
) {
      suspend operator fun invoke(): Int = repository.obtenerContador()
}