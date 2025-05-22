package com.example.marcacionesapp.presentation.marcacion

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MarcacionScreen(viewModel: MarcacionViewModel = hiltViewModel()) {

    val estadoMarcacion by viewModel.estadoMarcacion.collectAsState()
    val latitud by viewModel.latitud.collectAsState()
    val longitud by viewModel.longitud.collectAsState()

    var tipoMarcacion by remember { mutableIntStateOf(1) }
    var papeleta by remember { mutableStateOf("") }
    var mostrarCamara by remember { mutableStateOf(false) }

    val permisoCamara = rememberPermissionState(Manifest.permission.CAMERA)
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current


    val permisoUbicacion = rememberLauncherForActivityResult (
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                viewModel.iniciarUbicacion()
            } else {
                Toast.makeText(context, "Se necesita permiso de ubicación para marcar", Toast.LENGTH_LONG).show()
            }
        }
    )

    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            viewModel.iniciarUbicacion()
        } else {
            permisoUbicacion.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                viewModel.detenerUbicacion()
            } else if (event == Lifecycle.Event.ON_START) {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                if (hasPermission) {
                    viewModel.iniciarUbicacion()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (mostrarCamara && permisoCamara.status.isGranted) {
        CameraView(
            onCapture = { file ->
                mostrarCamara = false
                Toast.makeText(context, "Foto guardada: ${file.name}", Toast.LENGTH_SHORT).show()

                val rutaFoto = file.absolutePath
                viewModel.marcar(tipoMarcacion, rutaFoto, observacion = papeleta.ifBlank { null })

                tipoMarcacion = when (tipoMarcacion) {
                    1 -> 2 // Entrada -> Salida a Refrigerio
                    2 -> 3 // Salida a Refrigerio -> Regreso de Refrigerio
                    3 -> 4 // Regreso de Refrigerio -> Salida
                    4 -> 1 // Salida -> Entrada
                    5 -> 1 // Papeleta -> Entrada
                    else -> 1 //  Entrada
                }

                papeleta = ""
            }
        )
    }

    if (permisoCamara.status is PermissionStatus.Denied &&
        !(permisoCamara.status as PermissionStatus.Denied).shouldShowRationale) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Permiso de cámara requerido") },
            text = { Text("El permiso ha sido denegado permanentemente. Ve a configuración para activarlo.") },
            confirmButton = {
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = "package:${context.packageName}".toUri()
                    context.startActivity(intent)
                }) {
                    Text("Ir a configuración")
                }
            }
        )
    }

    if (!mostrarCamara) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Marcación Actual", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = estadoMarcacion,
                fontSize = 18.sp,
                color = if (estadoMarcacion.contains("Entrada")) Color(0xFF4CAF50) else Color.Gray
            )

            Spacer(modifier = Modifier.height(60.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(
                    1 to "Entrada",
                    2 to "Salida a Refrigerio",
                    3 to "Regreso de Refrigerio",
                    4 to "Salida",
                    5 to "Papeleta"
                ).forEach { (valor, marca) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = tipoMarcacion == valor,
                            onClick = { tipoMarcacion = valor },
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF1976D2))
                        )
                        Text(marca, fontSize = 18.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (tipoMarcacion == 5) {
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

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (tipoMarcacion == 5 && papeleta.isEmpty()) {
                        Toast.makeText(context, "El campo de papeleta es obligatorio", Toast.LENGTH_SHORT).show()
                    } else {
                        if (tipoMarcacion != 5) {
                            when {
                                permisoCamara.status.isGranted -> mostrarCamara = true
                                permisoCamara.status.shouldShowRationale -> {
                                    Toast.makeText(context, "Se necesita permiso de cámara para tomar la foto", Toast.LENGTH_LONG).show()
                                    permisoCamara.launchPermissionRequest()
                                }
                                else -> permisoCamara.launchPermissionRequest()
                            }
                        } else {
                            viewModel.marcar(tipoMarcacion, observacion = papeleta.ifBlank { null })
                            tipoMarcacion = 1
                            papeleta = ""
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
            ) {
                Icon(imageVector = Icons.Default.Face, contentDescription = "Marcar", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Registrar", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}
