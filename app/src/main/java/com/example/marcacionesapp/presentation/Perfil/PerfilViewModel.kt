package com.example.marcacionesapp.presentation.Perfil


import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.marcacionesapp.data.domain.model.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.AndroidViewModel
import com.example.marcacionesapp.data.dataStore.UsuarioSessionManager
import com.example.marcacionesapp.data.domain.usecase.ObtenerUsuarioUseCase

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val obtenerUsuarioUseCase: ObtenerUsuarioUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val sessionManager = UsuarioSessionManager(application.applicationContext)


    private val _usuario = mutableStateOf<Usuario?>(null)
    val usuario: State<Usuario?> = _usuario

    init {
        viewModelScope.launch {
            _usuario.value =obtenerUsuarioUseCase.execute()
        }
    }

    fun cerrarSesion(onLogout: () -> Unit) {
        viewModelScope.launch {
            sessionManager.cerrarSesion()
            onLogout()
        }
    }
}