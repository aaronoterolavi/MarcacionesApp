package com.example.marcacionesapp.data.mapper

import com.example.marcacionesapp.data.domain.model.Marcacion
import com.example.marcacionesapp.data.local.entity.MarcacionEntity
import com.example.marcacionesapp.data.remote.dto.MarcacionDto
import com.example.marcacionesapp.data.util.encodeFileToBase64



fun MarcacionEntity.toDomain(): Marcacion = Marcacion(
     id =id,
     iCodUsuario =iCodUsuario,
     iCodTipoMarcacion =iCodTipoMarcacion,
     dtFechaMarcacion =dtFechaMarcacion,
     vLatitud =vLatitud,
     vLongitud =vLongitud,
     estado =estado,
     vfoto =vfoto,
     contador =contador
)

fun Marcacion.toEntity(): MarcacionEntity = MarcacionEntity(
    id =id,
    iCodUsuario =iCodUsuario,
    iCodTipoMarcacion =iCodTipoMarcacion,
    dtFechaMarcacion =dtFechaMarcacion,
    vLatitud =vLatitud,
    vLongitud =vLongitud,
    estado =estado,
    vfoto =vfoto,
    contador =contador
)

fun Marcacion.toDto(): MarcacionDto = MarcacionDto(
        id = id,
        iCodUsuario = iCodUsuario,
        iCodTipoMarcacion = iCodTipoMarcacion,
        dtFechaMarcacion = dtFechaMarcacion,
        vLatitud = vLatitud,
        vLongitud = vLongitud,
        estado = estado,
        vfoto = encodeFileToBase64(vfoto.toString()),
        contador = contador
    )

