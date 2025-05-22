package com.example.marcacionesapp.data.dataStore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val USER_ID = intPreferencesKey("iCodUsuario")
    val USER_NOMBRE = stringPreferencesKey("vUsuario")
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
}