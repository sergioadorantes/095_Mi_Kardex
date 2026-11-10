package com.example.mikardex.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val AzulTec = Color(0xFF2DA3D2)

private val EsquemaClaro = lightColorScheme(
    primary = AzulTec,
    onPrimary = Color.White,
    secondary = AzulTec,
    background = Color(0xFFF4FAFD),
    onBackground = Color(0xFF10333F),
    surface = Color.White,
    onSurface = Color(0xFF10333F)
)

private val EsquemaOscuro = darkColorScheme(
    primary = AzulTec,
    onPrimary = Color.White,
    secondary = AzulTec,
    background = Color(0xFF101418),
    onBackground = Color.White,
    surface = Color(0xFF1B2229),
    onSurface = Color.White
)

@Composable
fun KardexTheme(modoOscuro: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (modoOscuro) EsquemaOscuro else EsquemaClaro,
        content = content
    )
}
