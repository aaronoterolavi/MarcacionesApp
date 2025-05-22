package com.example.marcacionesapp.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marcacionesapp.data.dataStore.UsuarioSessionManager
import com.example.marcacionesapp.data.domain.model.Usuario
import com.example.marcacionesapp.data.domain.usecase.LoginUseCase
import com.example.marcacionesapp.data.domain.usecase.ObtenerUsuarioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val obtenerUsuarioUseCase: ObtenerUsuarioUseCase,
    private val sessionManager: UsuarioSessionManager
) : ViewModel() {

    var uiState by mutableStateOf<LoginUiState>(LoginUiState.Idle)
        private set

    fun login(usuario: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            uiState = LoginUiState.Loading
            try {
                val user = loginUseCase.execute(usuario, password)
                sessionManager.guardarUsuarioSesion(user)
                uiState = LoginUiState.Success(user)
                onSuccess()
            } catch (e: Exception) {
                val usuarioOffLine = obtenerUsuarioUseCase.execute()
                if (usuarioOffLine != null && usuarioOffLine.vUsuario == usuario && usuarioOffLine.vPassword == password) {
                    sessionManager.guardarUsuarioSesion(usuarioOffLine)
                    uiState = LoginUiState.Success(usuarioOffLine)
                    onSuccess()
                } else {
                    uiState = LoginUiState.Error("Usuario o contraseña incorrectos")
                    onError()
                }
            }
        }
    }
}






sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val usuario: Usuario) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}