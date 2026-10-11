package com.example.registroestudiantes.model

data class Estudiante(
    val matricula: String = "",
    val nombre: String = "",
    val carrera: String = "",
    val turno: String = "Matutino",
    val activo: Boolean = true
)

val carreras = listOf(
    "Ingeniería en Mecatrónica",
    "Ingeniería en Sistemas Computacionales",
    "Ingeniería Industrial",
    "Licenciatura en Administración",
    "Licenciatura en Contaduría"
)
