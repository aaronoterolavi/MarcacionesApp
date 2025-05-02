package com.example.marcacionesapp

import android.app.Application
import com.example.marcacionesapp.data.dao.UsuarioDao
import com.example.marcacionesapp.data.entity.UsuarioEntity
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application()