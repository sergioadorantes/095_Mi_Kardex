package com.example.mikardex.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mikardex.KardexViewModel
import com.example.mikardex.data.Reticula
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(vm: KardexViewModel) {
    val promedio by vm.promedioGeneral.collectAsState()
    val obtenidos by vm.creditosObtenidosCarrera.collectAsState()
    val totales by vm.creditosTotalesCarrera.collectAsState()

    var menuAbierto by remember { mutableStateOf(false) }
    var verPromedio by remember { mutableStateOf(false) }

    val avance = if (totales > 0) obtenidos / totales.toFloat() else 0f
    val porcentaje = String.format(Locale.US, "%.1f", avance * 100)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Kardex", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    Box {
                        IconButton(onClick = { menuAbierto = true }) {
                            Text("☰", fontSize = 22.sp, color = Color.White)
                        }
                        DropdownMenu(
                            expanded = menuAbierto,
                            onDismissRequest = { menuAbierto = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Promedio general") },
                                onClick = { menuAbierto = false; verPromedio = true }
                            )
                            DropdownMenuItem(
                                text = { Text("Cerrar sesión") },
                                onClick = { menuAbierto = false; vm.cerrarSesion() }
                            )
                        }
                    }
                },
                actions = { BotonModoOscuro(vm.modoOscuro) { vm.alternarModoOscuro() } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AzulTec,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Encabezado: bienvenida + barra de progreso (ocupa las 2 columnas)
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(Modifier.fillMaxWidth()) {
                    Text(
                        "¡Bienvenido ${vm.preferencias.nombre}!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.height(14.dp))
                    Text(
                        "Avance de la carrera: $porcentaje%",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { avance },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(14.dp)
                            .clip(RoundedCornerShape(7.dp)),
                        color = AzulTec,
                        trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.15f)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "$obtenidos de $totales créditos",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }

            // 10 cuadros: 9 semestres + requisitos complementarios
            itemsIndexed(Reticula.nombresSemestre) { indice, nombre ->
                TarjetaSemestre(nombre) { vm.abrirSemestre(indice + 1) }
            }
        }
    }

    if (verPromedio) {
        AlertDialog(
            onDismissRequest = { verPromedio = false },
            title = { Text("Promedio general") },
            text = {
                Text(
                    formatoPromedio(promedio),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulTec
                )
            },
            confirmButton = { BotonKardex("Cerrar", onClick = { verPromedio = false }) }
        )
    }
}

@Composable
private fun TarjetaSemestre(nombre: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.aspectRatio(1f).clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = AzulTec, contentColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}
