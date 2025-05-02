package com.example.marcacionesapp.presentation.historial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marcacionesapp.data.entity.MarcacionEntity
import com.example.marcacionesapp.presentation.marcacion.MarcacionViewModel
import java.util.Date
import kotlin.collections.get


@Composable
fun HistorialMarcacionesScreen(viewModel: HistorialMarcacionesViewModel=hiltViewModel()) {
    // Obtenemos el flujo de marcaciones del ViewModel
    val historialMarcaciones = viewModel.historialMarcaciones.collectAsState().value

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(historialMarcaciones.size) { index ->
            val marcacion = historialMarcaciones[index]
            MarcacionItem(marcacion)
        }
    }
}


@Composable
fun MarcacionItem(marcacion: MarcacionEntity) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),

        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Tipo: ${marcacion.tipo}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Hora: ${Date(marcacion.timestamp)}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Estado: ${marcacion.estado}",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Lat: ${marcacion.latitud}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Long: ${marcacion.longitud}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            marcacion.foto?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Foto: $it",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
        }
    }
}
