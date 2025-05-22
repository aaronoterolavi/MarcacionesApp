package com.example.marcacionesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marcacionesapp.data.local.entity.MarcacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarcacionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertMarcacion(marcacion: MarcacionEntity)

    @Query("SELECT MAX(contador) FROM T_marcaciones")
    suspend fun obtenerContador(): Int?

    @Query("SELECT * FROM T_marcaciones ORDER BY dtFechaMarcacion DESC")
    fun getAllMarcaciones(): Flow<List<MarcacionEntity>>

    @Query("SELECT * FROM T_marcaciones WHERE estado = :estado")
      fun getMarcacionesPorEstado(estado: Int): Flow<List<MarcacionEntity>>

    @Query("UPDATE T_marcaciones SET estado = :estado WHERE id = :id")
    suspend fun actualizarEstadoMarcacion(id: Int, estado: Int)
}