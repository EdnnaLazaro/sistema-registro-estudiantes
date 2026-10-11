package com.example.registroestudiantes.ui.screens

import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.registroestudiantes.model.Estudiante

@Composable
fun DetalleScreen(estudiante: Estudiante, onVolver: () -> Unit, onNuevo: () -> Unit) {
    val context = LocalContext.current
    Scaffold(containerColor = MaterialTheme.colorScheme.background) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Text("¡Registro guardado!", style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary)
            Text("Confirma los datos del estudiante.")
            Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp)) {
                Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    CampoDetalle("Matrícula", estudiante.matricula)
                    CampoDetalle("Nombre completo", estudiante.nombre)
                    CampoDetalle("Carrera", estudiante.carrera)
                    CampoDetalle("Turno", estudiante.turno)
                    CampoDetalle("Estatus", if (estudiante.activo) "Activo" else "Inactivo")
                }
            }
            Text("Los datos se recuperarán al volver a abrir la aplicación.")
            Button(onClick = onNuevo, modifier = Modifier.fillMaxWidth()) { Text("Registrar otro estudiante") }
            OutlinedButton(onClick = onVolver, modifier = Modifier.fillMaxWidth()) { Text("Editar registro") }
            OutlinedButton(onClick = {
                val texto = """
                    Registro de estudiante
                    Matrícula: ${estudiante.matricula}
                    Nombre: ${estudiante.nombre}
                    Carrera: ${estudiante.carrera}
                    Turno: ${estudiante.turno}
                    Estatus: ${if (estudiante.activo) "Activo" else "Inactivo"}
                """.trimIndent()
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, texto)
                }
                context.startActivity(Intent.createChooser(intent, "Compartir registro"))
            }, modifier = Modifier.fillMaxWidth()) { Text("Compartir registro") }
        }
    }
}

@Composable
private fun CampoDetalle(etiqueta: String, valor: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(etiqueta, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary)
        Text(valor, style = MaterialTheme.typography.bodyLarge)
    }
}
