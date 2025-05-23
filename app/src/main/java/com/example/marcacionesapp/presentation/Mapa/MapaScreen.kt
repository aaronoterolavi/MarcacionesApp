package com.example.marcacionesapp.presentation.Mapa

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.marcacionesapp.data.helper.LocationHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

@Composable
fun MapaScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    val locationHelper = remember { LocationHelper(context.applicationContext) }

    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(-12.0464, -77.0428),
            10f
        )
    }

    val markerState = rememberMarkerState()
    var tienePermisoUbicacion by remember { mutableStateOf(false) }

    val solicitarPermisoUbicacion = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permisoConcedido ->
        tienePermisoUbicacion = permisoConcedido
        if (!permisoConcedido) {
            Toast.makeText(
                context,
                "Necesitas permiso de ubicación para continuar.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(Unit) {
        val permiso = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        tienePermisoUbicacion = permiso == PackageManager.PERMISSION_GRANTED
        if (!tienePermisoUbicacion) {
            solicitarPermisoUbicacion.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraState,
            properties = MapProperties(
                isMyLocationEnabled = tienePermisoUbicacion
            ),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false
            )
        ) {
            if (markerState.position != LatLng(0.0, 0.0)) {
                Marker(
                    state = markerState,
                    title = "Estás aquí"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    if (!tienePermisoUbicacion) {
                        solicitarPermisoUbicacion.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        return@Button
                    }

                    locationHelper.iniciarActualizacionUbicacion(
                        onUbicacionRecibida = { ubicacion ->
                            val coordenadas = LatLng(ubicacion.latitude, ubicacion.longitude)
                            markerState.position = coordenadas

                            scope.launch {
                                cameraState.animate(
                                    update = CameraUpdateFactory.newLatLngZoom(coordenadas, 16f)
                                )
                            }

                            locationHelper.detenerActualizacionUbicacion()
                        },
                        onError = { error ->
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a mi ubicación")
            }
        }
    }
}
