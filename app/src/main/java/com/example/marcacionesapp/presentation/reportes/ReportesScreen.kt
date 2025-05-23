package com.example.marcacionesapp.presentation.reportes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.marcacionesapp.presentation.componentes.MarcacionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportesScreen(
    viewModel: ReportesViewModel = hiltViewModel()
) {

    var estadoSeleccionado by remember { mutableIntStateOf(1) }


    val opcionesEstado = listOf(
        1 to "Pendiente",
        2 to "Enviado",
        3 to "Error"
    )


    val marcaciones by viewModel.obtenerMarcaciones(estadoSeleccionado).collectAsState()

    var expandir by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reporte de Marcaciones") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            ExposedDropdownMenuBox(
                expanded = expandir,
                onExpandedChange = { expandir = !expandir }
            ) {

                TextField(
                    readOnly = true,
                    value = opcionesEstado.first { it.first == estadoSeleccionado }.second,
                    onValueChange = {},
                    label = { Text("Selecciona un estado") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandir)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )


                ExposedDropdownMenu(
                    expanded = expandir,
                    onDismissRequest = { expandir = false }
                ) {
                    opcionesEstado.forEach { (valor, texto) ->
                        DropdownMenuItem(
                            text = { Text(texto) },
                            onClick = {
                                estadoSeleccionado = valor
                                expandir = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(marcaciones) { marcacion ->
                    MarcacionItem(marcacion)
                }
            }
        }
    }
}
