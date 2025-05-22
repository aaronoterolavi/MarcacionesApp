package com.example.marcacionesapp.presentation.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marcacionesapp.data.domain.model.Marcacion
import com.example.marcacionesapp.data.domain.usecase.ObtenerMarcacionesUseCase
import com.example.marcacionesapp.data.domain.usecase.SincronizarMarcacionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistorialMarcacionesViewModel @Inject constructor(
    private val obtenerMarcacionesUseCase: ObtenerMarcacionesUseCase,
    private val sincronizarMarcacionUseCase: SincronizarMarcacionUseCase
) : ViewModel() {

    private val _historialMarcaciones = MutableStateFlow<List<Marcacion>>(emptyList())
    val historialMarcaciones: StateFlow<List<Marcacion>> = _historialMarcaciones

    private val _estadoSincronizacion = MutableStateFlow(SincronizacionEstado())
    val estadoSincronizacion: StateFlow<SincronizacionEstado> = _estadoSincronizacion

    init {
        cargarMarcaciones()
    }

    fun cargarMarcaciones() {
        viewModelScope.launch {
            _historialMarcaciones.value = obtenerMarcacionesUseCase().first()
        }
    }

    fun sincronizarMarcaciones() {
        viewModelScope.launch {
            try {
                _estadoSincronizacion.value = SincronizacionEstado(cargando = true)

                val pendientes = _historialMarcaciones.value.filter { it.estado == 1 || it.estado == 3 }
                pendientes.forEach { marcacion ->
                    sincronizarMarcacionUseCase(marcacion)
                }

                cargarMarcaciones()
                _estadoSincronizacion.value = SincronizacionEstado(exito = true)
            } catch (e: Exception) {
                _estadoSincronizacion.value = SincronizacionEstado(mensajeError = "Error al sincronizar: ${e.message}")
            } finally {
                _estadoSincronizacion.value = _estadoSincronizacion.value.copy(cargando = false)
            }
        }
    }

    fun limpiarEstado() {
        _estadoSincronizacion.value = SincronizacionEstado()
    }
}

data class SincronizacionEstado(
    val cargando: Boolean = false,
    val mensajeError: String? = null,
    val exito: Boolean = false
)
