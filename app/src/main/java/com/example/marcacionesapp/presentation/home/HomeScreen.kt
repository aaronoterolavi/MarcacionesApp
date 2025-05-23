package com.example.marcacionesapp.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marcacionesapp.data.helper.LocationHelper
import com.example.marcacionesapp.presentation.Mapa.MapaScreen
import com.example.marcacionesapp.presentation.Perfil.PerfilScreen
import com.example.marcacionesapp.presentation.historial.HistorialMarcacionesScreen
import com.example.marcacionesapp.presentation.marcacion.MarcacionScreen
import com.example.marcacionesapp.presentation.reportes.ReportesScreen


@Composable
fun HomeScreen(rootnavController: NavController) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Marcacion.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Historial.route) {
                HistorialMarcacionesScreen()
            }
            composable(BottomNavItem.Marcacion.route) {
                MarcacionScreen()
            }

            composable(BottomNavItem.Mapa.route) {
                 MapaScreen()
            }
            composable(BottomNavItem.Reportes.route) {
                ReportesScreen()
            }
            composable(BottomNavItem.Perfil.route) {
                PerfilScreen(rootnavController)
            }
        }
    }
}