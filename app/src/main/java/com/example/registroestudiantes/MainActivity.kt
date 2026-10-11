package com.example.registroestudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.registroestudiantes.data.PreferencesManager
import com.example.registroestudiantes.navigation.AppNavigation
import com.example.registroestudiantes.ui.theme.RegistroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val preferences = remember { PreferencesManager(this@MainActivity) }
            var oscuro by rememberSaveable { mutableStateOf(preferences.cargarTemaOscuro()) }
            RegistroTheme(oscuro = oscuro) {
                AppNavigation(preferences = preferences, oscuro = oscuro, onCambiarTema = {
                    oscuro = it
                    preferences.guardarTemaOscuro(it)
                })
            }
        }
    }
}
