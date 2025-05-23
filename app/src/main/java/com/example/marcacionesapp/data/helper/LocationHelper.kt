package com.example.marcacionesapp.data.helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)
    private val settingsClient = LocationServices.getSettingsClient(context)

    private var locationCallback: LocationCallback? = null

    private val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        10000L
    ).build()

    fun verificarConfiguracionGPS(
        onDisponible: () -> Unit,
        onNoDisponible: (Exception?) -> Unit
    ) {
        val request = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()

        settingsClient.checkLocationSettings(request)
            .addOnSuccessListener {
                Log.d("LocationHelper", "GPS disponible.")
                onDisponible()
            }
            .addOnFailureListener { exception ->
                Log.w("LocationHelper", "GPS no disponible: ${exception.message}")
                onNoDisponible(exception)
            }
    }

        fun iniciarActualizacionUbicacion(
            onUbicacionRecibida: (Location) -> Unit,
            onError: (String) -> Unit = {}
        ) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                onError("Permiso de ubicación no otorgado.")
                Log.e("LocationHelper", "Permiso ACCESS_FINE_LOCATION denegado.")
                return
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation?.let {
                        Log.d("LocationHelper", "Ubicación recibida: ${it.latitude}, ${it.longitude}")
                        onUbicacionRecibida(it)
                    } ?: Log.w("LocationHelper", "No se pudo obtener la ubicación.")
                }
            }

            fusedLocationProvider.requestLocationUpdates(
                locationRequest,
                locationCallback!!,
                Looper.getMainLooper()
            )
        }

    fun detenerActualizacionUbicacion() {
        locationCallback?.let {
            fusedLocationProvider.removeLocationUpdates(it)
            Log.d("LocationHelper", "Actualizaciones de ubicación detenidas.")
        }
    }
}
