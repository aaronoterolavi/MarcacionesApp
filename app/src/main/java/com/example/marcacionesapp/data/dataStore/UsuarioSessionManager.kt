package com.example.marcacionesapp.data.dataStore


import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.marcacionesapp.data.domain.model.Usuario
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsuarioSessionManager @Inject constructor(
    @ApplicationContext private val context: Context) {

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
    }

    suspend fun guardarUsuarioSesion(usuario: Usuario) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = usuario.iCodUsuario
            preferences[PreferencesKeys.USER_NOMBRE] = usuario.vUsuario
            preferences[PreferencesKeys.IS_LOGGED_IN] = true
        }
    }

    fun obtenerUsuarioSesion(): Flow<Int?> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.USER_ID]
        }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { it.clear() }
    }

}