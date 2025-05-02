package com.example.marcacionesapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marcacionesapp.data.dao.MarcacionDao
import com.example.marcacionesapp.data.dao.UsuarioDao
import com.example.marcacionesapp.data.entity.MarcacionEntity
import com.example.marcacionesapp.data.entity.UsuarioEntity

@Database(entities = [UsuarioEntity::class, MarcacionEntity::class], version =1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UsuarioDao(): UsuarioDao
    abstract fun MarcacionDao(): MarcacionDao
}