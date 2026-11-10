package com.example.mikardex

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mikardex.data.AppDatabase
import com.example.mikardex.data.Materia
import com.example.mikardex.data.Preferencias
import com.example.mikardex.data.Reticula
import com.example.mikardex.data.TipoCurso
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class KardexViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.obtener(application).materiaDao()
    val preferencias = Preferencias(application)

    // ---- Estado de navegación y tema (sobrevive a rotaciones) ----
    var sesionIniciada by mutableStateOf(false)
        private set
    var semestreAbierto by mutableStateOf<Int?>(null)
        private set
    var modoOscuro by mutableStateOf(preferencias.modoOscuro)
        private set

    // ---- Datos globales ----
    private fun <T> Flow<T>.comoEstado(inicial: T): StateFlow<T> =
        stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), inicial)

    val promedioGeneral: StateFlow<Double?> = dao.promedioGeneral().comoEstado(null)
    val creditosObtenidosCarrera: StateFlow<Int> = dao.creditosObtenidosCarrera().comoEstado(0)
    val creditosTotalesCarrera: StateFlow<Int> = dao.creditosTotalesCarrera().comoEstado(0)

    init {
        // Primera ejecución: cargar la retícula (con las calificaciones de ejemplo del 1er semestre)
        viewModelScope.launch {
            if (dao.contar() == 0) dao.insertarTodas(Reticula.materias)
        }
    }

    // ---- Datos por semestre ----
    fun materias(semestre: Int): Flow<List<Materia>> = dao.materiasDelSemestre(semestre)
    fun promedio(semestre: Int): Flow<Double?> = dao.promedioDelSemestre(semestre)
    fun creditosTotales(semestre: Int): Flow<Int> = dao.creditosTotalesDelSemestre(semestre)
    fun creditosObtenidos(semestre: Int): Flow<Int> = dao.creditosObtenidosDelSemestre(semestre)

    // ---- Acciones ----
    fun guardarCalificacion(materia: Materia, tipo: TipoCurso, calificacion: Int?) {
        val actualizada = when (tipo) {
            TipoCurso.NORMAL -> materia.copy(calNormal = calificacion, calRepeticion = null, calEspecial = null)
            TipoCurso.REPETICION -> materia.copy(calRepeticion = calificacion, calEspecial = null)
            TipoCurso.ESPECIAL -> materia.copy(calEspecial = calificacion)
        }
        viewModelScope.launch { dao.actualizar(actualizada) }
    }

    fun iniciarSesion(control: String, contrasena: String): Boolean {
        val correcto = preferencias.credencialesValidas(control, contrasena)
        if (correcto) sesionIniciada = true
        return correcto
    }

    fun cerrarSesion() {
        sesionIniciada = false
        semestreAbierto = null
    }

    fun abrirSemestre(semestre: Int) { semestreAbierto = semestre }
    fun cerrarSemestre() { semestreAbierto = null }

    fun alternarModoOscuro() {
        modoOscuro = !modoOscuro
        preferencias.modoOscuro = modoOscuro
    }
}
