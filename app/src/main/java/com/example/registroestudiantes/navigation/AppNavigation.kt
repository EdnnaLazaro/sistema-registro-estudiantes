package com.example.registroestudiantes.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.registroestudiantes.data.PreferencesManager
import com.example.registroestudiantes.model.Estudiante
import com.example.registroestudiantes.ui.screens.*

@Composable
fun AppNavigation(preferences: PreferencesManager, oscuro: Boolean, onCambiarTema: (Boolean) -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "registro") {
        composable("registro") { entry ->
            val nuevo by entry.savedStateHandle.getStateFlow("nuevo", false).collectAsState()
            RegistroScreen(preferences = preferences, oscuro = oscuro, onCambiarTema = onCambiarTema,
                nuevoRegistro = nuevo,
                onNuevoConsumido = { entry.savedStateHandle["nuevo"] = false }, onRegistrar = { e ->
                preferences.guardar(e)
                // Codificar evita que espacios, acentos, /, ? y & alteren la ruta.
                navController.navigate(
                    "detalle?matricula=${Uri.encode(e.matricula)}&nombre=${Uri.encode(e.nombre)}" +
                    "&carrera=${Uri.encode(e.carrera)}&turno=${Uri.encode(e.turno)}&activo=${e.activo}"
                ) { launchSingleTop = true }
            })
        }
        composable(
            route = "detalle?matricula={matricula}&nombre={nombre}&carrera={carrera}&turno={turno}&activo={activo}",
            arguments = listOf(
                navArgument("matricula") { type = NavType.StringType },
                navArgument("nombre") { type = NavType.StringType },
                navArgument("carrera") { type = NavType.StringType },
                navArgument("turno") { type = NavType.StringType },
                navArgument("activo") { type = NavType.BoolType }
            )
        ) { entry ->
            val args = requireNotNull(entry.arguments)
            DetalleScreen(estudiante = Estudiante(
                matricula = args.getString("matricula").orEmpty(),
                nombre = args.getString("nombre").orEmpty(),
                carrera = args.getString("carrera").orEmpty(),
                turno = args.getString("turno").orEmpty(),
                activo = args.getBoolean("activo")
            ), onVolver = { navController.popBackStack() }, onNuevo = {
                navController.previousBackStackEntry?.savedStateHandle?.set("nuevo", true)
                navController.popBackStack()
            })
        }
    }
}
