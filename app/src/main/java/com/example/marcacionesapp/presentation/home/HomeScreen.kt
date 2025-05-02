package com.example.marcacionesapp.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marcacionesapp.presentation.Mapa.MapaScreen
import com.example.marcacionesapp.presentation.Perfil.PerfilScreen
import com.example.marcacionesapp.presentation.historial.HistorialMarcacionesScreen
import com.example.marcacionesapp.presentation.marcacion.MarcacionScreen
import com.example.marcacionesapp.presentation.marcacion.MarcacionViewModel

@Composable
fun HomeScreen(rootNavController: NavController) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Historial.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Historial.route) {
                HistorialMarcacionesScreen()
                //Text("Pantalla de HistorialScreen")
            }
            composable(BottomNavItem.Marcacion.route) {
               val vm: MarcacionViewModel=hiltViewModel()
                MarcacionScreen(vm)
                //Text("Pantalla de MarcacionScreen")
            }

            composable(BottomNavItem.Mapa.route) {
                 MapaScreen()
                //Text("Pantalla de MapaScreen")
            }
            composable(BottomNavItem.Reportes.route) {
                // ReportesScreen()
                Text("Pantalla de ReportesScreen")
            }
            composable(BottomNavItem.Perfil.route) {
                PerfilScreen(rootNavController)
                // Text("Pantalla de PerfilScreen")
            }
        }
    }
}