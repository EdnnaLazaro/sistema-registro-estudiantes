package com.example.registroestudiantes.data

import android.content.Context
import com.example.registroestudiantes.model.Estudiante

class PreferencesManager(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences("registro_estudiantes", Context.MODE_PRIVATE)

    fun guardar(e: Estudiante) {
        prefs.edit().putString("matricula", e.matricula)
            .putString("nombre", e.nombre).putString("carrera", e.carrera)
            .putString("turno", e.turno).putBoolean("activo", e.activo).apply()
    }

    fun guardarTemaOscuro(oscuro: Boolean) {
        prefs.edit().putBoolean("tema_oscuro", oscuro).apply()
    }

    fun cargarTemaOscuro(): Boolean = prefs.getBoolean("tema_oscuro", false)

    fun cargar() = Estudiante(
        matricula = prefs.getString("matricula", "") ?: "",
        nombre = prefs.getString("nombre", "") ?: "",
        carrera = prefs.getString("carrera", "") ?: "",
        turno = prefs.getString("turno", "Matutino") ?: "Matutino",
        activo = prefs.getBoolean("activo", true)
    )
}
