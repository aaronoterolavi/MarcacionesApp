package com.example.marcacionesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marcacionesapp.data.local.entity.UsuarioEntity

@Dao
interface UsuarioDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertarUsuario(usuario: UsuarioEntity)

        @Query("SELECT * FROM T_Usuario WHERE iCodUsuario = :id LIMIT 1")
        suspend fun obtenerUsuario(id: Int): UsuarioEntity?
}
