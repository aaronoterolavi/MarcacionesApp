package com.example.marcacionesapp.di

import android.content.Context
import androidx.room.Room
import com.example.marcacionesapp.data.local.dao.MarcacionDao
import com.example.marcacionesapp.data.local.dao.UsuarioDao
import com.example.marcacionesapp.data.local.db.AppDatabase
import com.example.marcacionesapp.data.remote.api.MarcacionApi
import com.example.marcacionesapp.data.remote.api.UsuarioApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object  NetworkModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "marcaciones.db").build()

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://bfd2-190-237-231-178.ngrok-free.app/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideMarcacionDao(db: AppDatabase): MarcacionDao = db.marcacionDao()

    @Provides
    fun provideUsuarioDao(db: AppDatabase): UsuarioDao=db.usuarioDao()

    @Provides
    fun provideUsuarioApi(retrofit: Retrofit): UsuarioApi =
        retrofit.create(UsuarioApi::class.java)

    @Provides
    fun provideMarcacionApi(retrofit: Retrofit): MarcacionApi =
        retrofit.create(MarcacionApi::class.java)


}