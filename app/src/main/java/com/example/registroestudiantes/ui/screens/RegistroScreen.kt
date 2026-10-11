package com.example.registroestudiantes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.registroestudiantes.data.PreferencesManager
import com.example.registroestudiantes.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    preferences: PreferencesManager,
    oscuro: Boolean,
    onCambiarTema: (Boolean) -> Unit,
    nuevoRegistro: Boolean,
    onNuevoConsumido: () -> Unit,
    onRegistrar: (Estudiante) -> Unit
) {
    var ultimo by remember { mutableStateOf(preferences.cargar()) }
    // rememberSaveable conserva lo escrito al girar el dispositivo.
    // SharedPreferences recupera el último registro tras cerrar la aplicación.
    var matricula by rememberSaveable { mutableStateOf(ultimo.matricula) }
    var nombre by rememberSaveable { mutableStateOf(ultimo.nombre) }
    var carrera by rememberSaveable { mutableStateOf(ultimo.carrera) }
    var turno by rememberSaveable { mutableStateOf(ultimo.turno) }
    var activo by rememberSaveable { mutableStateOf(ultimo.activo) }
    var validar by rememberSaveable { mutableStateOf(false) }
    var expandido by remember { mutableStateOf(false) }

    fun limpiarFormulario() {
        matricula = ""
        nombre = ""
        carrera = ""
        turno = "Matutino"
        activo = true
        validar = false
        expandido = false
    }

    LaunchedEffect(nuevoRegistro) {
        if (nuevoRegistro) {
            limpiarFormulario()
            onNuevoConsumido()
        }
    }

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).imePadding()
            .verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Sistema de Registro de Estudiantes", style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary)
            Text("Captura los datos y confirma tu registro.")
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Tema oscuro", style = MaterialTheme.typography.titleSmall)
                Switch(checked = oscuro, onCheckedChange = onCambiarTema)
            }
            if (ultimo.matricula.isNotBlank()) {
                Text("Último registro recuperado: ${ultimo.matricula}",
                    color = MaterialTheme.colorScheme.secondary)
            }
            Card(shape = RoundedCornerShape(24.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(value = matricula, onValueChange = { matricula = it },
                        label = { Text("Matrícula") }, singleLine = true,
                        isError = validar && matricula.isBlank(),
                        supportingText = { if (validar && matricula.isBlank()) Text("Ingresa la matrícula") },
                        modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = nombre, onValueChange = { nombre = it },
                        label = { Text("Nombre completo") }, singleLine = true,
                        isError = validar && nombre.isBlank(),
                        supportingText = { if (validar && nombre.isBlank()) Text("Ingresa el nombre completo") },
                        modifier = Modifier.fillMaxWidth())
                    ExposedDropdownMenuBox(expanded = expandido,
                        onExpandedChange = { expandido = it }) {
                        OutlinedTextField(value = carrera, onValueChange = {}, readOnly = true,
                            label = { Text("Carrera") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
                            isError = validar && carrera !in carreras,
                            modifier = Modifier.menuAnchor().fillMaxWidth())
                        ExposedDropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
                            carreras.forEach { opcion ->
                                DropdownMenuItem(text = { Text(opcion) }, onClick = {
                                    carrera = opcion
                                    expandido = false
                                })
                            }
                        }
                    }
                    if (validar && carrera !in carreras) Text("Selecciona una carrera",
                        color = MaterialTheme.colorScheme.error)
                    HorizontalDivider()
                    Text("Turno", style = MaterialTheme.typography.titleMedium)
                    Column(Modifier.selectableGroup()) {
                        listOf("Matutino", "Vespertino").forEach { opcion ->
                            Row(Modifier.fillMaxWidth().selectable(selected = turno == opcion,
                                onClick = { turno = opcion }, role = Role.RadioButton).padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = turno == opcion, onClick = null)
                                Spacer(Modifier.width(12.dp))
                                Text(opcion)
                            }
                        }
                    }
                    HorizontalDivider()
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("Estatus", style = MaterialTheme.typography.titleMedium)
                            Text(if (activo) "Activo" else "Inactivo")
                        }
                        Switch(checked = activo, onCheckedChange = { activo = it })
                    }
                }
            }
            Button(onClick = {
                validar = true
                if (matricula.isNotBlank() && nombre.isNotBlank() && carrera in carreras) {
                    ultimo = Estudiante(matricula.trim(), nombre.trim(), carrera, turno, activo)
                    onRegistrar(ultimo)
                }
            }, modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)) {
                Text("Guardar y ver detalle")
            }
            OutlinedButton(onClick = { limpiarFormulario() }, modifier = Modifier.fillMaxWidth()) {
                Text("Limpiar formulario")
            }
            Text("Se conservarán los datos del último registro en este dispositivo.",
                style = MaterialTheme.typography.bodySmall)
        }
    }
}
