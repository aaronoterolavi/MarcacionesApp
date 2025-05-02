package com.example.marcacionesapp.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Marcacion : BottomNavItem("marcacion", "Marcación", Icons.Default.Home)
    object Historial : BottomNavItem("historial", "Historial", Icons.Default.List)

    object Mapa : BottomNavItem("mapa", "Mapa", Icons.Default.Place)
    object Reportes : BottomNavItem("reporte", "Reporte", Icons.Filled.List)
    object Perfil : BottomNavItem("perfil", "Perfil", Icons.Default.Person)
}