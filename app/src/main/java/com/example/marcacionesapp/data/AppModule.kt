package com.example.marcacionesapp.data

import android.content.Context
import androidx.room.Room
import com.example.marcacionesapp.data.dao.MarcacionDao
import com.example.marcacionesapp.data.dao.UsuarioDao
import com.example.marcacionesapp.data.db.AppDatabase
import com.example.marcacionesapp.data.entity.UsuarioEntity
import com.example.marcacionesapp.data.repository.MarcacionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "marcaciones.db").build()

    @Provides
    fun provideLoginDao(db: AppDatabase): UsuarioDao = db.UsuarioDao()

    @Provides
    fun provideMarcacionDao(db: AppDatabase): MarcacionDao = db.MarcacionDao()



}