package com.example.marcacionesapp.data.domain.usecase

import com.example.marcacionesapp.data.domain.model.Marcacion
import com.example.marcacionesapp.data.domain.repository.MarcacionRepository
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RegistrarMarcacionUseCase @Inject constructor(
    private val marcacionRepository: MarcacionRepository,
    private val obtenerContadorUseCase: ObtenerContadorUseCase
) {
    suspend operator fun invoke(
        usuarioId: Int?,
        tipoMarcacion: Int,
        latitud: Double?,
        longitud: Double?,
        rutaFoto: String?
    ) {
        val timestamp = System.currentTimeMillis()
        val dateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            ZoneId.systemDefault()
        )
        val fecha = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        val contador = obtenerContadorUseCase() + 1

        val marcacion = Marcacion(
            iCodUsuario = usuarioId,
            iCodTipoMarcacion = tipoMarcacion,
            dtFechaMarcacion = fecha,
            vLatitud = (latitud ?: 0.0).toString(),
            vLongitud = (longitud ?: 0.0).toString(),
            estado = 1,
            vfoto = rutaFoto,
            contador = contador
        )

        marcacionRepository.registrarMarcacion(marcacion)
    }
}