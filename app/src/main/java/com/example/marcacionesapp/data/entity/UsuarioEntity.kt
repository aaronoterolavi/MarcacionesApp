package com.example.marcacionesapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//ELIMNAR CLASE CON TODO Y CARPETA UNA VES TENGAMOS EL API
@Entity
data class UsuarioEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuario: String,
    val password: String


){
    // Constructor vacío necesario para Room
    constructor() : this(0, "", "")
}