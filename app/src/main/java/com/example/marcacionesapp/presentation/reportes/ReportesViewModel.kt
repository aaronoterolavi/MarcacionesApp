package com.example.marcacionesapp.presentation.reportes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marcacionesapp.data.domain.model.Marcacion
import com.example.marcacionesapp.data.domain.usecase.ObtenerMarcacionesPorEstadoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ReportesViewModel @Inject constructor(
    private val obtenerMarcacionesPorEstadoUseCase: ObtenerMarcacionesPorEstadoUseCase
) : ViewModel() {

    fun obtenerMarcaciones(estado: Int): StateFlow<List<Marcacion>> {
        return obtenerMarcacionesPorEstadoUseCase(estado)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList())
    }
}