package com.example.marcacionesapp.data.mapper

import com.example.marcacionesapp.data.domain.model.Usuario
import com.example.marcacionesapp.data.local.entity.UsuarioEntity
import com.example.marcacionesapp.data.remote.dto.UsuarioDto


    fun UsuarioDto.toDomain(): Usuario = Usuario(
        iCodUsuario=iCodUsuario,
        vUsuario =vUsuario ,
        vPassword =vPassword ,
        vNombres =vNombres ,
        vApellidoPaterno =vApellidoPaterno ,
        vApellidoMaterno =vApellidoMaterno ,
        vDni =vDni ,
        vSede =vSede ,
        dtFechaIngreso =dtFechaIngreso ,
        vCorreoElectronico =vCorreoElectronico ,
        vTelefono  =vTelefono
    )

    fun UsuarioEntity.toDomain(): Usuario = Usuario(
        iCodUsuario=iCodUsuario,
        vUsuario = vUsuario,
        vPassword =vPassword ,
        vNombres =vNombres ,
        vApellidoPaterno =vApellidoPaterno ,
        vApellidoMaterno =vApellidoMaterno ,
        vDni =vDni ,
        vSede =vSede ,
        dtFechaIngreso =dtFechaIngreso ,
        vCorreoElectronico =vCorreoElectronico ,
        vTelefono  =vTelefono
    )

    fun Usuario.toEntity(): UsuarioEntity = UsuarioEntity(
        iCodUsuario=iCodUsuario,
        vUsuario =vUsuario ,
        vPassword =vPassword ,
        vNombres =vNombres ,
        vApellidoPaterno =vApellidoPaterno ,
        vApellidoMaterno =vApellidoMaterno ,
        vDni =vDni ,
        vSede =vSede ,
        dtFechaIngreso =dtFechaIngreso ,
        vCorreoElectronico =vCorreoElectronico ,
        vTelefono  =vTelefono
    )
