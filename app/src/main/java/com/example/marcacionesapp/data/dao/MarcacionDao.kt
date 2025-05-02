package com.example.marcacionesapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marcacionesapp.data.entity.MarcacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarcacionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertMarcacion(marcacion: MarcacionEntity)

    @Query("SELECT MAX(contador) FROM MarcacionEntity")
    suspend fun obtenerContador(): Int?

    @Query("SELECT * FROM MarcacionEntity ORDER BY timestamp DESC")
    fun getAllMarcaciones(): Flow<List<MarcacionEntity>>

    @Query("SELECT * FROM MarcacionEntity WHERE estado = :estado")
    suspend fun getMarcacionesPorEstado(estado: String): List<MarcacionEntity>
}