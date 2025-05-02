package com.example.marcacionesapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marcacionesapp.data.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: UsuarioEntity)

    @Query("SELECT * FROM UsuarioEntity WHERE usuario = :usuario AND password = :password")
    suspend fun login(usuario: String, password: String): UsuarioEntity?

    @Query("SELECT * FROM UsuarioEntity WHERE usuario = :usuario")
            suspend fun getUsuarioPorNombre(usuario:String): UsuarioEntity?

}