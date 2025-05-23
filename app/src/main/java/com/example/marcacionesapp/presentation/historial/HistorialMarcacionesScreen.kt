package com.example.marcacionesapp.presentation.historial

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import com.example.marcacionesapp.presentation.componentes.MarcacionItem
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialMarcacionesScreen(viewModel: HistorialMarcacionesViewModel = hiltViewModel()) {

    val historialMarcaciones by viewModel.historialMarcaciones.collectAsState()
    val estadoSincronizacion by viewModel.estadoSincronizacion.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(estadoSincronizacion.mensajeError) {
        estadoSincronizacion.mensajeError?.let { errores ->
            errores.lines().forEach { errorMsg ->
                snackbarHostState.showSnackbar(errorMsg)
                delay(2000)
            }
            viewModel.limpiarEstado()
        }
    }

    LaunchedEffect(estadoSincronizacion.exito) {
        if (estadoSincronizacion.exito) {
            snackbarHostState.showSnackbar("Sincronización completada correctamente")
            viewModel.limpiarEstado()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Marcaciones") },
                actions = {
                    IconButton(
                        onClick = { viewModel.sincronizarMarcaciones() },
                        enabled = !estadoSincronizacion.cargando
                    ) {
                        if (estadoSincronizacion.cargando) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Sync,
                                contentDescription = "Sincronizar"
                            )

                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(historialMarcaciones.size) { index ->
                val marcacion = historialMarcaciones[index]
                MarcacionItem(marcacion)
            }
        }
    }
}


