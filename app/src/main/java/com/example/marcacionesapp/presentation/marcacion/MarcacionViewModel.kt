package com.example.marcacionesapp.presentation.marcacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marcacionesapp.data.dataStore.UsuarioSessionManager
import com.example.marcacionesapp.data.entity.MarcacionEntity
import com.example.marcacionesapp.data.entity.MarcacionEstado
import com.example.marcacionesapp.data.repository.MarcacionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarcacionViewModel @Inject constructor(
    private val marcacionRepository: MarcacionRepository,
    private val usuarioSessionManager:UsuarioSessionManager
): ViewModel() {

    private val _usuarioId = MutableStateFlow<Int?>(null)
    val usuarioId : StateFlow<Int?> = _usuarioId

    private val _estadoMarcacion = MutableStateFlow("No Marcado")
    val estadoMarcacion: StateFlow<String> = _estadoMarcacion

    private val _latitud = MutableStateFlow<Double?>(null)
    val latitud: StateFlow<Double?> = _latitud

    private val _longitud = MutableStateFlow<Double?>(null)
    val longitud: StateFlow<Double?> = _longitud
    private var contador = 0

    init {
        viewModelScope.launch {
            usuarioSessionManager.obtenerUsuarioSesion().collect {
                _usuarioId.value = it
            }
        }
    }

    fun marcar(tipo: String, rutaFoto: String? = null) {
        viewModelScope.launch {
            val timestamp = System.currentTimeMillis()
            contador++

            val entity = MarcacionEntity(
                usuarioId = _usuarioId.value,
                tipo = tipo,
                timestamp = timestamp,
                latitud = _latitud.value ?: 0.0,
                longitud = _longitud.value ?: 0.0,
                estado = MarcacionEstado.PENDIENTE,
                foto = rutaFoto,
                contador = contador
            )

            marcacionRepository.registrarMarcacion(entity)
            _estadoMarcacion.value = "Marcación registrada: $tipo"
           // obtenerHistorial()
        }
    }


}