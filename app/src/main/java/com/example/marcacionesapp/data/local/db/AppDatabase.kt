package com.example.marcacionesapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marcacionesapp.data.local.dao.MarcacionDao
import com.example.marcacionesapp.data.local.dao.UsuarioDao
import com.example.marcacionesapp.data.local.entity.MarcacionEntity
import com.example.marcacionesapp.data.local.entity.UsuarioEntity

@Database(entities = [UsuarioEntity::class, MarcacionEntity::class], version =1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun marcacionDao(): MarcacionDao
}