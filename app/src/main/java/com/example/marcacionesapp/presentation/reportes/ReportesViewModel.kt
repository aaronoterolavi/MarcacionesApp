package com.example.marcacionesapp.presentation.reportes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marcacionesapp.data.domain.model.Marcacion
import com.example.marcacionesapp.data.domain.usecase.ObtenerMarcacionesPorEstadoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ReportesViewModel @Inject constructor(
    private val obtenerMarcacionesPorEstadoUseCase: ObtenerMarcacionesPorEstadoUseCase
) : ViewModel() {

    private val _estadoSeleccionado = MutableStateFlow(1)
    val estadoSeleccionado: StateFlow<Int> = _estadoSeleccionado

    val marcaciones: StateFlow<List<Marcacion>> = _estadoSeleccionado
        .flatMapLatest { estado ->

                (obtenerMarcacionesPorEstadoUseCase(estado))

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun setEstadoSeleccionado(estado: Int) {
        _estadoSeleccionado.value = estado
    }
}