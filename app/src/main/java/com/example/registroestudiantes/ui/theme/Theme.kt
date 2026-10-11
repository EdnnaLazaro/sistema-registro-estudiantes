package com.example.registroestudiantes.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun RegistroTheme(oscuro: Boolean = false, content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (oscuro) darkColorScheme(
        primary = Color(0xFFFFA9CA), onPrimary = Color(0xFF5C1232),
        secondary = Color(0xFFE8B5CB), background = Color(0xFF1B1117),
        surface = Color(0xFF241A20), onBackground = Color(0xFFF3DDE7),
        onSurface = Color(0xFFF3DDE7), surfaceVariant = Color(0xFF51414B),
        onSurfaceVariant = Color(0xFFE3BFCF), primaryContainer = Color(0xFF7D2850),
        onPrimaryContainer = Color(0xFFFFD9E5)
    ) else lightColorScheme(
        primary = Color(0xFFB52E63), onPrimary = Color.White,
        secondary = Color(0xFF7A4059), background = Color(0xFFFFF0F5),
        surface = Color(0xFFFFFBFF), primaryContainer = Color(0xFFFFD9E5),
        onPrimaryContainer = Color(0xFF421026)
    ), content = content)
}
