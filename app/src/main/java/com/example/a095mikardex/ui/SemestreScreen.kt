package com.example.mikardex.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mikardex.KardexViewModel
import com.example.mikardex.data.Materia
import com.example.mikardex.data.Reticula
import com.example.mikardex.data.TipoCurso
import com.example.mikardex.data.aprobada
import com.example.mikardex.data.calificacionDe
import com.example.mikardex.data.calificacionFinal
import com.example.mikardex.data.tipoCurso

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SemestreScreen(vm: KardexViewModel, semestre: Int) {
    val materias by remember(semestre) { vm.materias(semestre) }
        .collectAsState(initial = emptyList())
    val creditosTotales by remember(semestre) { vm.creditosTotales(semestre) }
        .collectAsState(initial = 0)
    val creditosObtenidos by remember(semestre) { vm.creditosObtenidos(semestre) }
        .collectAsState(initial = 0)
    val promedio by remember(semestre) { vm.promedio(semestre) }
        .collectAsState(initial = null)

    var editando by remember { mutableStateOf<Materia?>(null) }
    var verPromedio by remember { mutableStateOf(false) }

    val nombreSemestre = Reticula.nombresSemestre[semestre - 1]

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(nombreSemestre, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                        Text("Créditos totales: $creditosTotales", fontSize = 14.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { vm.cerrarSemestre() }) {
                        Text("←", fontSize = 24.sp, color = Color.White)
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
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Créditos obtenidos: $creditosObtenidos de $creditosTotales",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(10.dp))
                    BotonKardex(
                        texto = "Ver promedio semestral",
                        onClick = { verPromedio = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(materias, key = { it.id }) { materia ->
                TarjetaMateria(materia) { editando = materia }
            }
        }
    }

    editando?.let { materia ->
        EditarCalificacionDialog(
            materia = materia,
            onDismiss = { editando = null },
            onGuardar = { tipo, calificacion ->
                vm.guardarCalificacion(materia, tipo, calificacion)
                editando = null
            }
        )
    }

    if (verPromedio) {
        AlertDialog(
            onDismissRequest = { verPromedio = false },
            title = { Text("Promedio semestral") },
            text = {
                Column {
                    Text(nombreSemestre)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        formatoPromedio(promedio),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulTec
                    )
                }
            },
            confirmButton = { BotonKardex("Cerrar", onClick = { verPromedio = false }) }
        )
    }
}

@Composable
private fun TarjetaMateria(materia: Materia, onClick: () -> Unit) {
    val calFinal = materia.calificacionFinal
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, AzulTec)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(materia.nombre, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Spacer(Modifier.height(2.dp))
                Text("Créditos: ${materia.creditos}", fontSize = 14.sp)
                if (calFinal != null) {
                    Text("Curso: ${materia.tipoCurso?.etiqueta}", fontSize = 14.sp)
                } else {
                    Text(
                        "Sin calificación · toca para capturar",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            if (calFinal != null) {
                Column(horizontalAlignment = Alignment.End) {
                    Text("Calificación", fontSize = 12.sp)
                    Text(
                        "$calFinal",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (materia.aprobada) AzulTec else Color(0xFFE53935)
                    )
                }
            }
        }
    }
}

@Composable
private fun EditarCalificacionDialog(
    materia: Materia,
    onDismiss: () -> Unit,
    onGuardar: (TipoCurso, Int?) -> Unit
) {
    var tipo by remember { mutableStateOf(materia.tipoCurso ?: TipoCurso.NORMAL) }
    var texto by remember(tipo) {
        mutableStateOf(materia.calificacionDe(tipo)?.toString() ?: "")
    }
    val valor = if (texto.isEmpty()) 0 else texto.toIntOrNull()
    val valido = texto.isEmpty() || (valor != null && valor in 0..100)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(materia.nombre) },
        text = {
            Column {
                Text("¿Cómo se cursó la materia?")
                TipoCurso.entries.forEach { opcion ->
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { tipo = opcion },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = tipo == opcion, onClick = { tipo = opcion })
                        Text(opcion.etiqueta)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = texto,
                    onValueChange = { nuevo ->
                        if (nuevo.length <= 3 && nuevo.all { it.isDigit() }) texto = nuevo
                    },
                    label = { Text("Calificación (0-100)") },
                    singleLine = true,
                    isError = !valido,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    supportingText = { Text("Vacío = 0") }
                )
            }
        },
        confirmButton = {
            BotonKardex("Guardar", onClick = { onGuardar(tipo, valor) }, habilitado = valido)
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
