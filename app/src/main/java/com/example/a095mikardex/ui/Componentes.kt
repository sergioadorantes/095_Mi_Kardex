package com.example.mikardex.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a095mikardex.R
import java.util.Locale

/** Logo azul en modo claro, logo blanco en modo oscuro. */
@Composable
fun LogoTec(modoOscuro: Boolean, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(
            if (modoOscuro) R.drawable.logo_tec_blanco else R.drawable.logo_tec_azul
        ),
        contentDescription = "Logo del Tecnológico",
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}

/** Botón blanco con texto azul (borde azul para que se vea sobre fondo claro). */
@Composable
fun BotonKardex(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    habilitado: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = habilitado,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, AzulTec),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = AzulTec,
            disabledContainerColor = Color.White.copy(alpha = 0.5f),
            disabledContentColor = AzulTec.copy(alpha = 0.5f)
        )
    ) {
        Text(texto, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BotonModoOscuro(modoOscuro: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Text(if (modoOscuro) "☀️" else "🌙", fontSize = 20.sp)
    }
}

fun formatoPromedio(valor: Double?): String =
    if (valor == null) "Sin calificaciones" else String.format(Locale.US, "%.2f", valor)
