package com.example.marcacionesapp.data.util

import android.util.Base64
import java.io.File

fun encodeFileToBase64(path: String): String? {
        return try {
            val file = File(path)
            val bytes = file.readBytes()
            Base64.encodeToString(bytes, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
