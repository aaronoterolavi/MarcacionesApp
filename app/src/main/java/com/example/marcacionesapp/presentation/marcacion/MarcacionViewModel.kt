package com.example.marcacionesapp.presentation.marcacion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marcacionesapp.data.dataStore.UsuarioSessionManager
import com.example.marcacionesapp.data.domain.model.Marcacion
import com.example.marcacionesapp.data.domain.usecase.ObtenerContadorUseCase
import com.example.marcacionesapp.data.domain.usecase.RegistrarMarcacionUseCase
import com.example.marcacionesapp.data.helper.LocationHelper

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MarcacionViewModel @Inject constructor(
    private val registrarMarcacionUseCase: RegistrarMarcacionUseCase,
    private val usuarioSessionManager: UsuarioSessionManager,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _usuarioId = MutableStateFlow<Int?>(null)

    private val _estadoMarcacion = MutableStateFlow("No Marcado")
    val estadoMarcacion: StateFlow<String> = _estadoMarcacion

    private val _latitud = MutableStateFlow<Double?>(null)
    val latitud: StateFlow<Double?> = _latitud

    private val _longitud = MutableStateFlow<Double?>(null)
    val longitud: StateFlow<Double?> = _longitud



    init {
        viewModelScope.launch {
            usuarioSessionManager.obtenerUsuarioSesion().collect {
                _usuarioId.value = it
            }
        }
        iniciarUbicacion()
    }

    fun iniciarUbicacion() {
        locationHelper.verificarConfiguracionGPS(
            onDisponible = {
                locationHelper.iniciarActualizacionUbicacion(
                    onUbicacionRecibida = { location ->
                        _latitud.value = location.latitude
                        _longitud.value = location.longitude
                        Log.d("MarcacionViewModel", "Ubicación actual: ${location.latitude}, ${location.longitude}")
                    },
                    onError = { mensaje ->
                        _estadoMarcacion.value = "Error al obtener ubicación: $mensaje"
                        Log.e("MarcacionViewModel", mensaje)
                    }
                )
            },
            onNoDisponible = { exception ->
                _estadoMarcacion.value = "GPS desactivado. Actívalo para registrar ubicación."
                exception?.message?.let {
                    Log.w("MarcacionViewModel", "GPS no disponible: $it")
                }
            }
        )
    }

    fun detenerUbicacion() {
        locationHelper.detenerActualizacionUbicacion()

    }

    fun marcar(tipo: Int, rutaFoto: String? = null, observacion:String?) {
        viewModelScope.launch {
            registrarMarcacionUseCase(
                usuarioId = _usuarioId.value,
                tipoMarcacion = tipo,
                latitud = _latitud.value,
                longitud = _longitud.value,
                rutaFoto = rutaFoto
            )
            _estadoMarcacion.value = "Marcación registrada: $tipo"
        }
    }
}

