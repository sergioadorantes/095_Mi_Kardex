package com.example.mikardex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mikardex.ui.HomeScreen
import com.example.mikardex.ui.KardexTheme
import com.example.mikardex.ui.LoginScreen
import com.example.mikardex.ui.SemestreScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: KardexViewModel = viewModel()
            KardexTheme(modoOscuro = vm.modoOscuro) {
                // El botón "atrás" del teléfono cierra el semestre y vuelve al inicio
                BackHandler(enabled = vm.sesionIniciada && vm.semestreAbierto != null) {
                    vm.cerrarSemestre()
                }
                val semestre = vm.semestreAbierto
                when {
                    !vm.sesionIniciada -> LoginScreen(vm)
                    semestre != null -> SemestreScreen(vm, semestre)
                    else -> HomeScreen(vm)
                }
            }
        }
    }
}
