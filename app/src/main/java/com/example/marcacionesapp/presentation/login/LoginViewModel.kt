package com.example.marcacionesapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marcacionesapp.data.dataStore.UsuarioSessionManager
import com.example.marcacionesapp.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: LoginRepository,
    private val sessionManager: UsuarioSessionManager
) : ViewModel() {

    fun login(usuario: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            val user = repo.login(usuario, password)
            if (user != null) {
                sessionManager.guardarUsuarioSesion(user)
                onSuccess()
            } else {
                onError()
            }
        }
    }
}
