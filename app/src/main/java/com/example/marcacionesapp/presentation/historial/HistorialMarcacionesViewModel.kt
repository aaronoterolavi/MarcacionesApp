package com.example.marcacionesapp.presentation.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marcacionesapp.data.dataStore.UsuarioSessionManager
import com.example.marcacionesapp.data.entity.MarcacionEntity
import com.example.marcacionesapp.data.repository.MarcacionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistorialMarcacionesViewModel @Inject constructor(
    marcacionRepository: MarcacionRepository,
): ViewModel() {



    val historialMarcaciones: StateFlow<List<MarcacionEntity>> =
        marcacionRepository.obtenerMarcaciones().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )


}
