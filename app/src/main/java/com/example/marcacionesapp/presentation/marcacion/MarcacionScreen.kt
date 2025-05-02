package com.example.marcacionesapp.presentation.marcacion

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marcacionesapp.presentation.login.LoginViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MarcacionScreen(viewModel: MarcacionViewModel) {

    val estadoMarcacion by viewModel.estadoMarcacion.collectAsState()
    var tipoMarcacion by remember { mutableStateOf("Entrada") }
    var papeleta by remember { mutableStateOf("") }
    val latitud by viewModel.latitud.collectAsState()
    val longitud by viewModel.longitud.collectAsState()
    val context= LocalContext.current
    var mostrarCamara by remember { mutableStateOf(false) }
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    // Mostrar la cámara si el permiso fue otorgado
    if (mostrarCamara && cameraPermissionState.status.isGranted) {
        CameraView(
            onCapture = { file ->
                mostrarCamara = false
                Toast.makeText(context, "Foto guardada: ${file.name}", Toast.LENGTH_SHORT).show()

                val rutaFoto = file.absolutePath

                tipoMarcacion = when (tipoMarcacion) {
                    "Entrada" -> "Salida"
                    "Salida_A_Refrigerio" -> "Regreso_De_Refrigerio"
                    "Regreso_De_Refrigerio" -> "Salida"
                    "Papeleta" -> "Entrada"
                    else -> "Entrada"
                }
                viewModel.marcar(tipoMarcacion, rutaFoto)
            }
        )
    }

    // Si el permiso fue denegado permanentemente
    if (cameraPermissionState.status is PermissionStatus.Denied &&
        !(cameraPermissionState.status as PermissionStatus.Denied).shouldShowRationale) {

        AlertDialog(
            onDismissRequest = { /* Evita que se cierre al tocar fuera del cuadro */ },
            title = {
                Text("Permiso de cámara requerido")
            },
            text = {
                Text("El permiso de cámara ha sido denegado permanentemente. Por favor, ve a configuración para habilitarlo.")
            },
            confirmButton = {
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:${context.packageName}")
                    context.startActivity(intent)
                }) {
                    Text("Ir a configuración")
                }
            },
//            dismissButton = {
//                Button(onClick = { /* Podrías cerrar la app o volver atrás */ }) {
//                    Text("Cancelar")
//                }
//            }
        )
    }


    if (mostrarCamara) {
        CameraView(
            onCapture = { file ->
                mostrarCamara = false
                Toast.makeText(context, "Foto guardada: ${file.name}", Toast.LENGTH_SHORT).show()

                val rutaFoto = file.absolutePath


                viewModel.marcar(tipoMarcacion, rutaFoto)
            }
        )
    }else{


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        Text("Marcación Actual", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = estadoMarcacion,
            fontSize = 18.sp,
            color = if (estadoMarcacion.contains("Entrada")) Color(0xFF4CAF50) else Color.Gray
        )

        Spacer(modifier = Modifier.height(60.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = tipoMarcacion == "Entrada",
                    onClick = { tipoMarcacion = "Entrada" },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF1976D2))
                )
                Text("Entrada", fontSize = 18.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = tipoMarcacion == "Salida_A_Refrigerio",
                    onClick = { tipoMarcacion = "Salida_A_Refrigerio" },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF1976D2))
                )
                Text("Salida a Refrigerio", fontSize = 18.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = tipoMarcacion == "Regreso_De_Refrigerio",
                    onClick = { tipoMarcacion = "Regreso_De_Refrigerio" },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF1976D2))
                )
                Text("Regreso de Refrigerio", fontSize = 18.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = tipoMarcacion == "Salida",
                    onClick = { tipoMarcacion = "Salida" },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF1976D2))
                )
                Text("Salida", fontSize = 18.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = tipoMarcacion == "Papeleta",
                    onClick = { tipoMarcacion = "Papeleta" },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF1976D2))
                )
                Text("Papeleta", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (tipoMarcacion == "Papeleta") {
            TextField(
                value = papeleta,
                onValueChange = { papeleta = it },
                label = { Text("Observación / Papeleta") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Lat: ${latitud ?: "?"}, Long: ${longitud ?: "?"}")

        Button(
            onClick = {
//                if (tipoMarcacion == "Papeleta" && papeleta.isEmpty()) {
//                    Toast.makeText(context, "El campo de papeleta es obligatorio", Toast.LENGTH_SHORT).show()
//                } else {
//                    if (tipoMarcacion != "Papeleta") {
//                        mostrarCamara = true
//                        //viewModel.marcar(tipoMarcacion)
//                        //Toast.makeText(context, tipoMarcacion, Toast.LENGTH_SHORT).show()
//                    } else {
//                        viewModel.marcar(tipoMarcacion)
//                        tipoMarcacion = "Entrada" // Despues de Marcar la papeleta se va a entrada
//                    }
//                }
                if (tipoMarcacion == "Papeleta" && papeleta.isEmpty()) {
                    Toast.makeText(context, "El campo de papeleta es obligatorio", Toast.LENGTH_SHORT).show()
                }else{
                    if(tipoMarcacion != "Papeleta"){
                        when{
                            cameraPermissionState.status.isGranted ->{
                                mostrarCamara=true
                            }
                            cameraPermissionState.status.shouldShowRationale ->{
                                Toast.makeText(context,"Se necesita permiso de cámara para tomar la foto", Toast.LENGTH_LONG).show()
                                cameraPermissionState.launchPermissionRequest()
                            }
                            else ->{
                                cameraPermissionState.launchPermissionRequest()
                            }
                        }
                    }else{
                        viewModel.marcar(tipoMarcacion)
                        tipoMarcacion = "Entrada"
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Marcar",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Marcar $tipoMarcacion", color = Color.White, fontSize = 18.sp)
        }


    }
}
}