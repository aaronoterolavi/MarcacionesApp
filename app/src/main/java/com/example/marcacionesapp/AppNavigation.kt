package com.example.marcacionesapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marcacionesapp.data.dataStore.UsuarioSessionManager
import com.example.marcacionesapp.presentation.home.HomeScreen
import com.example.marcacionesapp.presentation.login.LoginScreen
import androidx.compose.runtime.getValue

@Composable
fun AppNavigation(sessionManager: UsuarioSessionManager) {
    val navController = rememberNavController()
    val isLoggedIn by sessionManager.isLoggedIn.collectAsState(initial = false)

    LaunchedEffect(isLoggedIn) {
        navController.navigate(if (isLoggedIn) "home" else "login") {
            popUpTo(0) { inclusive = true }
        }
    }

    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("home") {
            HomeScreen(rootnavController = navController)
        }
    }
}