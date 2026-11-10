package com.example.mikardex.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mikardex.KardexViewModel

@Composable
fun LoginScreen(vm: KardexViewModel) {
    var control by remember { mutableStateOf(vm.preferencias.numeroControl) }
    var contrasena by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Scaffold { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 28.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LogoTec(vm.modoOscuro, Modifier.height(140.dp))
                Spacer(Modifier.height(16.dp))
                Text(
                    "Mi Kardex",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(32.dp))

                OutlinedTextField(
                    value = control,
                    onValueChange = { control = it; error = false },
                    label = { Text("Número de control") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it; error = false },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )
                if (error) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Número de control o contraseña incorrectos",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(Modifier.height(24.dp))
                BotonKardex(
                    texto = "Iniciar sesión",
                    onClick = { if (!vm.iniciarSesion(control, contrasena)) error = true },
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                )
            }
            // Botón de tema encima del Column para que reciba los toques
            Box(Modifier.align(Alignment.TopEnd)) {
                BotonModoOscuro(vm.modoOscuro) { vm.alternarModoOscuro() }
            }
        }
    }
}
