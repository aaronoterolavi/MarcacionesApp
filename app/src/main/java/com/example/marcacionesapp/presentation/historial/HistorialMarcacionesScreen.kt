package com.example.marcacionesapp.presentation.historial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marcacionesapp.data.domain.model.Marcacion
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialMarcacionesScreen(viewModel: HistorialMarcacionesViewModel = hiltViewModel()) {

    val historialMarcaciones by viewModel.historialMarcaciones.collectAsState()
    val estadoSincronizacion by viewModel.estadoSincronizacion.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(estadoSincronizacion.mensajeError) {
        estadoSincronizacion.mensajeError?.let {
            snackbarHostState.showSnackbar(it)
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


@Composable
fun MarcacionItem(marcacion: Marcacion) {
    val estadoDescripcion = when (marcacion.estado) {
        1 -> "Pendiente"
        2 -> "Enviado"
        3 -> "Error"
        else -> "Desconocido"
    }

    val tipoMarcaDescripcion = when (marcacion.iCodTipoMarcacion) {
        1 -> "Entrada"
        2 -> "Salida a Refrigerio"
        3 -> "Regreso de Regrigerio"
        4 -> "Salida"
        5 -> "Papeleta"
        else -> "Desconocido"
    }

    val cardColor = when (marcacion.estado) {
        1 -> Color(0xFFFFF59D)
        2 -> Color(0xFFC8E6C9)
        3 -> Color(0xFFFFCDD2)
        else -> Color.LightGray
    }
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),

        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Tipo: ${tipoMarcaDescripcion}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Hora: ${ (marcacion.dtFechaMarcacion)}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Estado: $estadoDescripcion",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Lat: ${marcacion.vLatitud}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Long: ${marcacion.vLongitud}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            marcacion.vfoto?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Foto: $it",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
        }
    }
}
